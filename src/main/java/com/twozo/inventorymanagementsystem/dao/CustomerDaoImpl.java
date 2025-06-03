package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Profile("dev")
public class CustomerDaoImpl implements CustomerDao<Customer> {

    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

    public CustomerDaoImpl(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean exists(final String phoneNo) {
        final String query = "SELECT id, name, phone_no, city, pincode FROM \"inventoryDB\".\"Customer\" WHERE phone_no = ?";

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNo);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("Found the customer");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Find Operation failed", e);
        }

        return false;
    }

    @Override
    public int store(final Customer customer, final Connection connection) {

        try {
            final boolean isAvailable = exists(customer.getPhoneNo());

            if (isAvailable) {
                final Customer existingCustomer = get(customer.getPhoneNo());
                return existingCustomer.getId();
            }

            final String query = "INSERT INTO \"inventoryDB\".\"Customer\"(name, phone_no, city, pincode) VALUES (?, ?, ?, ?) ;";

            try (final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, customer.getName());
                stmt.setString(2, customer.getPhoneNo());
                stmt.setString(3, customer.getCity());
                stmt.setInt(4, customer.getPincode());
                final int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Inserting customer failed. No rows affected.");
                }

                try (final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                    if (generatedKeys.next()) {
                        logger.info("Customer details are stored and id is generated");
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Duplicate value ! Inserting customer failed. No ID obtained.");
                    }

                }

            }

        } catch (SQLException e) {
            logger.error("Store operation failed", e);
            return -1;
        }

    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("This operation is not supported ");
    }

    @Override
    public int store(final Customer customer) {
        final String query = "INSERT INTO \"inventoryDB\".\"Customer\"(name, phone_no, city, pincode) VALUES (?, ?, ?, ?) ;";

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNo());
            stmt.setString(3, customer.getCity());
            stmt.setInt(4, customer.getPincode());
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting customer failed. No rows affected.");
            }

            try (final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    logger.info("Customer details are stored and id is generated");
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Duplicate value ! Inserting customer failed. No ID obtained.");
                }

            }

        } catch (SQLException e) {
            logger.error("Store operation failed", e);
            return -1;
        }

    }

    @Override
    public Customer get(String phoneNo) {
        final String query = "SELECT id, name, phone_no, city, pincode FROM \"inventoryDB\".\"Customer\" WHERE phone_no = ?";

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNo);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("Found the customer");
                return new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("city"),
                        resultSet.getInt("pincode")
                );
            }

        } catch (SQLException e) {
            logger.error("Find Operation failed", e);
        }

        return null;
    }

}
