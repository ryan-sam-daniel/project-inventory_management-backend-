package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

@Repository
@Profile("dev")
public class PaymentDaoImpl implements PaymentDao<Payment> {

    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);

    public PaymentDaoImpl(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int store(final Payment payment, final Connection connection) {
        final String query = "Insert into \"inventoryDB\".\"Transaction\" (method, amount ,trnx_type, date) Values (?, ?, CAST(? AS \"inventoryDB\".transaction_type), ?)";

        try (final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, payment.getMethod());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getType());
            stmt.setDate(4, Date.valueOf(payment.getDate()));
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting Payment Failed ! No rows affected");
            }

            try (final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    logger.info("payment stored and id is generated");
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting Payment Failed ! No id generated");
                }

            }

        } catch (final SQLException e) {
            logger.error("Store operation failed", e);
        }

        return -1;
    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("this operation is not supported");
    }

    @Override
    public boolean exists(final int id) {
        final String query = "select id, method, amount, trnx_type, date from \"inventoryDB\".\"Transaction\" where id = ?";

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            final ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                logger.info("Found the Payment info");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Find Operation Failed", e);
        }

        return false;
    }

    @Override
    public Payment get(int id) {
        final String query = "select id, method, amount, trnx_type, date from \"inventoryDB\".\"Transaction\" where id = ?";

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            final ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                logger.info("Found the Payment info");
                return new Payment(resultSet.getInt("id"), resultSet.getString("method"), resultSet.getDouble("amount"), resultSet.getString("trnx_type"), resultSet.getDate("date").toLocalDate());
            }

        } catch (SQLException e) {
            logger.error("Find Operation Failed", e);
        }

        return null;
    }

}
