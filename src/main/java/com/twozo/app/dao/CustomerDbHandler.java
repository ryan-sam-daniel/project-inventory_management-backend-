package com.twozo.app.dao;

import com.twozo.app.model.Customer;
import com.twozo.app.utility.CustomerQuery;
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
public class CustomerDbHandler implements StakeHolderDbHandler<Customer> {
    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(CustomerDbHandler.class);

    public CustomerDbHandler(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Customer checkExistence(final String phoneNo){
        final String query = CustomerQuery.SELECT_CUSTOMER_BY_PHONE;

//          PreparedStatement - precompiled and store in the object for efficient execution
//          Mostly used for parametric query
        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, phoneNo);
//          executeQuery() - execute the query stored in the object
            final ResultSet resultSet = preparedStatement.executeQuery();

//          initially cursor before first row when next() is called moved to the first row and so on
            if (resultSet.next()){
                logger.info("Found the customer");
                return new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone_no"),
                    resultSet.getString("city"),
                    resultSet.getInt("pincode")
                );
            }

        } catch(SQLException e){
            logger.error("Find Operation failed",e);
        }
        return null;
    }

    @Override
    public int store(final Customer customer, final Connection connection) {

        try {
            // Step 1: Check if customer exists based on phone number
            final Customer existingCustomer = checkExistence(customer.getPhoneNo());

            if (existingCustomer != null) {
                return existingCustomer.getId();
            }

            // Step 2: If not exists, insert the new customer
            final String query = CustomerQuery.INSERT_CUSTOMER;

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
            logger.error("Store operation failed",e);
            return -1;
        }

    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("This operation is not supported ");
    }

    @Override
    public int store(final Customer customer) {
        final String query = CustomerQuery.INSERT_CUSTOMER;

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
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
            logger.error("Store operation failed",e);
            return -1;
        }
    }

}
