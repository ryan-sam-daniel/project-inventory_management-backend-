package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.PurchaseItem;
import com.twozo.inventorymanagementsystem.model.SaleItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("dev")
public class ProductDaoImpl implements ProductDao<Product> {
    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    public ProductDaoImpl(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Product> getAll() {
        final String query = "SELECT id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity FROM \"inventoryDB\".\"Product\"";

        try(final Connection connection = dataSource.getConnection()){
            final PreparedStatement stmt = connection.prepareStatement(query);

            try(final ResultSet resultSet = stmt.executeQuery()){
                final Collection<Product> inventory = new ArrayList<>();

                while(resultSet.next()){
                    inventory.add(new Product(resultSet.getInt(1)
                    ,resultSet.getString(2)
                    ,resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getInt(8)));
                }

                logger.info("Got all the product from inventory");
                return inventory;
            }
        } catch(SQLException e){
            logger.error("Failed to get all product",e);
        }

        return null;
    }

    @Override
    public Product get(final int id){
        final String query = "SELECT id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity FROM \"inventoryDB\".\"Product\" WHERE id = ?";

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,id);

            try(final ResultSet resultSet = stmt.executeQuery()){

                if(resultSet.next()){
                    return (new Product(resultSet.getInt(1)
                    ,resultSet.getString(2)
                    ,resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getInt(8)));
                }

                logger.info("Got the product by id");
            }
        } catch(SQLException e){
            logger.error("Failed to get the product ",e);
        }

        return null;
    }

    @Override
    public int store(final Product product) {
        final String query = "INSERT INTO \"inventoryDB\".\"Product\" (name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPurchasePrice());
            stmt.setDouble(3, product.getMrp());
            stmt.setDouble(4, product.getTaxPercentage());
            stmt.setDouble(5, product.getDiscountPercentage());
            stmt.setDouble(6, product.getSellingPrice());
            stmt.setInt(7, product.getStockQuantity());
            final int rows = stmt.executeUpdate();

            if (rows == 0){
                throw new SQLException("Inserting Product Failed ! No rows affected");
            }

            try(final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()){
                    logger.info("Product stored and id is generated");
                    return generatedKeys.getInt(1);
                } else{
                    throw new SQLException("Inserting Product Failed ! No id generated");
                }

            }

        } catch (SQLException e) {
            logger.error("Failed to store the product",e);
        }

        return -1;
    }

    @Override
    public int store(Product product, Connection connection) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean remove(final int id){
        final String query =  "DELETE FROM \"inventoryDB\".\"Product\" WHERE id = ?";

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, id);
            final int row = stmt.executeUpdate();

            if (row > 0){
                logger.info("Product removed successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Failed to remove the product",e);
        }

        return false;
    }

    @Override
    public boolean updateStock(final Collection<PurchaseItem> cart){
        final String query = "UPDATE \"inventoryDB\".\"Product\" SET stock_quantity = (stock_quantity + ?) WHERE id = ?";

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query)) {

            for (PurchaseItem item : cart){
                stmt.setInt(1, item.getQuantity());
                stmt.setInt(2, item.getProductId());
                stmt.addBatch();
            }

            stmt.executeBatch();
            logger.info("Stock updation via batch processing is completed");

        } catch (SQLException e) {
            logger.error("Failed to update the stock",e);
            return false;
        }

        return true;
    }

    @Override
    public boolean removeStock(final Collection<SaleItem> cart) {
        final String query = "UPDATE \"inventoryDB\".\"Product\" SET stock_quantity = (stock_quantity - ?) WHERE id = ?";

        try (final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query)) {

            for (SaleItem item : cart) {
                stmt.setInt(1, item.getQuantity());
                stmt.setInt(2, item.getProductId());
                stmt.addBatch();
            }

            stmt.executeBatch();
            logger.info("Stock removal via batch processing is completed");
        } catch (SQLException e) {
            logger.error("Failed to remove the stock",e);
            return false;
        }

        return true;
    }

    public boolean update(final Product product, final List<String> queryList){

        if (queryList.isEmpty()) {
            logger.warn("No fields to update for product ID: " + product.getId());
            return false;
        }

        StringBuilder query = new StringBuilder("Update \"inventoryDB\".\"Product\" set ");

        for (int i=0 ; i<queryList.size() ; i++){
            query.append(queryList.get(i));
            System.out.println(queryList.get(i));
            if(i < queryList.size()-1){
                query.append(",");
            }
        }

        query.append(" where id = ").append(product.getId());
        System.out.println(query);

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0){
                logger.info("Updated successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Failed to remove the stock",e);
            return false;
        }

        return false;
    }

}

