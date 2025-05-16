package com.twozo.app.dao;

import com.twozo.app.model.Vendor;
import com.twozo.app.utility.VendorQuery;
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
public class VendorDbHandler implements StakeHolderDbHandler<Vendor> {
    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(VendorDbHandler.class);

    public VendorDbHandler(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Vendor checkExistence(final String phoneNo){
        final String query = VendorQuery.SELECT_Vendor_BY_PHONE;

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, phoneNo);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Vendor(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone_no"),
                    resultSet.getString("city"),
                    resultSet.getInt("pincode")
                );
            }

        } catch(SQLException e){
            logger.error("Error Finding the vendor",e);
        }

        return null;
    }

    public int store(final Vendor vendor,final Connection connection){
        final Vendor existingVendor = checkExistence(vendor.getPhoneNo());

        if (existingVendor != null) {
            return existingVendor.getId();
        }

        final String query = VendorQuery.INSERT_VENDOR;

        try(final PreparedStatement stmt = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getPhoneNo());
            stmt.setString(3, vendor.getCity());
            stmt.setInt(4, vendor.getPincode());
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Inserting Vendor Failed ! No rows affected");
            }

            try(final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()){
                    logger.info("Vendor details are stored and id is generated");
                    return generatedKeys.getInt(1);
                }
                else{
                    throw new SQLException("Duplicate value ! Inserting Vendor Failed ! No id generated");
                }

            }
        } catch (final Exception e) {
            logger.error("Store operation failed",e);
            return -1;
        }

    }

    @Override
    public boolean remove(int id) {
        throw new UnsupportedOperationException("This method is not used");
    }

    @Override
    public int store(final Vendor vendor){
        final String query = VendorQuery.INSERT_VENDOR;

        try(final Connection connection = dataSource.getConnection();
            final PreparedStatement stmt = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getPhoneNo());
            stmt.setString(3, vendor.getCity());
            stmt.setInt(4, vendor.getPincode());
            final int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Inserting Vendor Failed ! No rows affected");
            }

            try(final ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()){
                    logger.info("Vendor details are stored and id is generated");
                    return generatedKeys.getInt(1);
                }
                else{
                    throw new SQLException("Duplicate value ! Inserting Vendor Failed ! No id generated");
                }

            }
        } catch (SQLException e) {
            logger.error("Store operation failed",e);
            return -1;
        }

    }

}
