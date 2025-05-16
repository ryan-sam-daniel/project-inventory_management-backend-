package com.twozo.app.dao;

import com.twozo.app.model.Payment;
import com.twozo.app.model.Purchase;
import com.twozo.app.model.PurchaseItem;
import com.twozo.app.model.Vendor;
import com.twozo.app.utility.PurchaseQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
@Profile("dev")
public class PurchaseDbHandler implements TradeDbHandler<Vendor, Purchase, PurchaseItem> {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseDbHandler.class);
    private final StakeHolderDbHandler<Vendor> vendorDAO;
    private final PaymentDbManager paymentDbManager;
    private final DataSource dataSource;

    public PurchaseDbHandler(final StakeHolderDbHandler<Vendor> vendorDAO, final PaymentDbManager paymentDbManager, final DataSource dataSource){
        this.vendorDAO = vendorDAO;
        this.paymentDbManager = paymentDbManager;
        this.dataSource = dataSource;
    }

    @Override
    public boolean processTransaction(final Vendor vendor, final Payment payment, final Purchase purchase, final List<PurchaseItem> cart) {
        Connection connection = null;
        Savepoint savepoint = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // Store vendor
            final int vendorId = vendorDAO.store(vendor, connection);

            if (vendorId == -1) {
                connection.rollback(savepoint);
                logger.error("vendor operation failed in purchase transaction");
                return false;
            }

            // Store payment
            final int paymentId = paymentDbManager.store(payment, connection);

            if (paymentId == -1) {
                connection.rollback(savepoint);
                logger.error("Payment operation failed in purchase transaction");
                return false;
            }

            // Add these IDs to purchase
            purchase.setVendorId(vendorId);
            purchase.setPaymentId(paymentId);

            // Store purchase
            final int purchaseId = store(purchase, connection);

            if (purchaseId == -1) {
                connection.rollback(savepoint);
                logger.error("Storing purchase failed");
                return false;
            }

            // Store purchase items
            if (!storeItem(cart, purchaseId, connection)) {
                connection.rollback(savepoint);
                logger.error("Storing purchase item failed");
                return false;
            }

            connection.commit();
            logger.info("Purchase Transaction is successfully");
            return true;
        } catch (SQLException e) { //      if inseting or getting connection fails  this ctach block will execute
            try {
                // we are rolling back to the savepoint
                if (connection != null) {
                    connection.rollback(savepoint);
                }

            } catch (SQLException ex) {
                // this will execute when the rollback fails
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
    public int store(final Purchase purchase, final Connection connection){
        final String query = PurchaseQuery.INSERT_PURCHASE;

        try(final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, purchase.getVendorId());
            stmt.setInt(2, purchase.getPaymentId());
            stmt.setObject(3,purchase.getPurchaseDate());
            stmt.setDouble(4, purchase.getSubTotal());
            stmt.setDouble(5,purchase.getFinalAmount());
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Insertion failed ! no rows affected");
            }

            try(final ResultSet resultSet = stmt.getGeneratedKeys()){

                if(resultSet.next()){
                    return resultSet.getInt(1);
                } else{
                    throw new SQLException("Inserting Purchase_Order Failed ! No id generated");
                }

            }

        } catch(SQLException e){
            logger.error("Storing failed",e);
        }

    return -1;
    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("This operation is not valid");
    }

    @Override
    public boolean storeItem(final List<PurchaseItem> cart, final int purchaseId, final Connection connection){

        for (final PurchaseItem purchase : cart){
            final String query = PurchaseQuery.INSERT_PURCHASE_ITEM;

            try(final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
                stmt.setInt(1, purchaseId);
                stmt.setInt(2, purchase.getProductId());
                stmt.setInt(3,purchase.getQuantity());
                stmt.setDouble(4, purchase.getPurchasePrice());
                stmt.setDouble(5, purchase.getSubTotal());
                stmt.setDouble(6, purchase.getTaxAmount());
                stmt.setDouble(7,purchase.getFinalDiscountedAmount());
                final int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0){
                    throw new SQLException("Insertion failed ! no rows affected");
                }

            } catch(SQLException e){
                logger.error("Storing item failed",e);
                return false;
            }
        }

        return true;
    }

}
