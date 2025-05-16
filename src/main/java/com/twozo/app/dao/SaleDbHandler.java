package com.twozo.app.dao;

import com.twozo.app.model.Customer;
import com.twozo.app.model.Payment;
import com.twozo.app.model.Sale;
import com.twozo.app.model.SaleItem;
import com.twozo.app.utility.SaleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
@Profile("dev")
public class SaleDbHandler implements TradeDbHandler<Customer, Sale, SaleItem> {
    private final static Logger logger = LoggerFactory.getLogger(SaleDbHandler.class);
    private final StakeHolderDbHandler<Customer> customerDbHandler;
    private final PaymentDbManager paymentDbManager;
    private final DataSource dataSource;

    public SaleDbHandler(final StakeHolderDbHandler<Customer> customerDbHandler, final PaymentDbManager paymentDbManager, final DataSource dataSource){
        this.customerDbHandler = customerDbHandler;
        this.paymentDbManager = paymentDbManager;
        this.dataSource = dataSource;
    }

    public boolean processTransaction(final Customer customer, final Payment payment, final Sale sale, final List<SaleItem> cart) {
        Connection connection = null;
        Savepoint savepoint = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            // Store customer
            final int customerId = customerDbHandler.store(customer, connection);

            if (customerId == -1) {
                connection.rollback(savepoint);
                logger.error("Customer operation failed in purchase transaction");
                return false;
            }

            // Store payment
            final int paymentId = paymentDbManager.store(payment, connection);

            if (paymentId == -1) {
                connection.rollback(savepoint);
                logger.error("Payment operation failed in purchase transaction");
                return false;
            }

            // Add these IDs to sale
            sale.setCustomerId(customerId);
            sale.setPaymentId(paymentId);
            // Store sale
            final int saleId = store(sale, connection);

            if (saleId == -1) {
                connection.rollback(savepoint);
                logger.error("Storing purchase failed");
                return false;
            }

            // Store sale items
            if (!storeItem(cart, saleId, connection)) {
                connection.rollback(savepoint);
                logger.error("Storing purchase item failed");
                return false;
            }

            connection.commit();
            logger.info("Purchase Transaction is successfully");
            return true;
        } catch (SQLException e) {

            try {

                if (connection != null) {
                    connection.rollback(savepoint);
                }

            } catch (SQLException ex) {
                logger.error("Rollback fails",ex);
            }

            logger.error("Purchase Transaction is failed",e);
            return false;
        } finally {

            try {

                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }

            } catch (SQLException e) {
                logger.error("Connection is not closed",e);
            }

        }

    }

    
    @Override
    public int store(final Sale sale, final Connection connection){
        final String query = SaleQuery.INSERT_SALE;

        try(final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, sale.getCustomerId());
            stmt.setInt(2, sale.getPaymentId());
            stmt.setObject(3,sale.getBillDate());
            stmt.setDouble(4, sale.getSubTotal());
            stmt.setDouble(5,sale.getFinalAmount());
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Insertion failed ! no rows affected");
            }

            try(final ResultSet resultSet = stmt.getGeneratedKeys()){

                if(resultSet.next()){
                    return resultSet.getInt(1);
                }
                else{
                    throw new SQLException("Inserting Sale_Order Failed ! No id generated");
                }

            }

        } catch(SQLException e){
            logger.error("Storing failed",e);
        }

    return -1;
    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("This method is not valid");
    }

    @Override
    public boolean storeItem(final List<SaleItem> cart,final int saleId, final Connection connection){

        for (final SaleItem sale : cart){
            final String query = SaleQuery.INSERT_SALE_ITEM;

            try(final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
                stmt.setInt(1, saleId);
                stmt.setInt(2, sale.getProductId());
                stmt.setInt(3,sale.getQuantity());
                stmt.setDouble(4, sale.getSellingPrice());
                stmt.setDouble(5, sale.getSubTotal());
                stmt.setDouble(6, sale.getTaxAmount());
                stmt.setDouble(7,sale.getFinalDiscountedAmount());
                final int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0){
                    throw new SQLException("Insertion failed ! no rows affected");
                }

            }
            catch(SQLException e){
                logger.error("Storing item failed",e);
                return false;
            }

        }
        return true;
    }
}
