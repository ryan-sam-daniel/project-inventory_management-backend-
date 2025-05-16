package com.twozo.app.dao;

import com.twozo.app.model.Product;
import com.twozo.app.model.Report;
import com.twozo.app.utility.ReportQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
@Profile("dev")
public class ReportDbHandler implements ReportDbManager {
    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(ReportDbHandler.class);

    public ReportDbHandler(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Report get() {
        final Report report = new Report(null, null, 0, 0, null, 0, 0);
        Connection connection = null;

        try {
            // Begin transaction
            connection =dataSource.getConnection();
            connection.setAutoCommit(false);

            // 1. Max Sold Product
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.MAX_SALED_PRODUCT);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setMaxSold(resultSet.getString(1));
                }

            }

            // 2. Min Sold Product
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.MIN_SALED_PRODUCT);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setMinSold( resultSet.getString(1));
                }

            }

            // 3. Total Sales in Last 7 Days
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.TOTAL_SALES_IN_DATE);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setWeekSale(resultSet.getDouble(1));
                }

            }

            // 4. Total Purchase in Last 7 Days
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.TOTAL_PURCHASE_IN_DATE);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setWeekPurchase(resultSet.getDouble(1));
                }

            }


            // 5. Stock Check
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.STOCK_CHECK);
                 final ResultSet resultSet = stmt.executeQuery()) {
                final HashMap<Integer, Product> lowStockMap = new HashMap<>();

                while (resultSet.next()) {
                    final int id = resultSet.getInt(1);
                    Product product = new Product(resultSet.getInt(1)
                                                ,resultSet.getString(2)
                                                ,resultSet.getDouble(3),
                                                resultSet.getDouble(4),
                                                resultSet.getInt(5),
                                                resultSet.getDouble(6),
                                                resultSet.getDouble(7),
                                                resultSet.getInt(8));
                    lowStockMap.put(id, product);
                }

                report.setLowStockItems(lowStockMap);
            }

            // 6. Total Sales
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.TOTAL_SALES);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setTotalSale(resultSet.getDouble(1));
                }

            }

            // 7. Total Purchase
            try (final PreparedStatement stmt = connection.prepareStatement(ReportQuery.TOTAL_PURCHASE);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setTotalPurchase(resultSet.getDouble(1));
                }

            }

            // Commit if all succeed
            connection.commit();
            logger.info("Report Generated Successfully");
            return report;
        } catch (SQLException e) {

            // Rollback if any query fails
            try {
                connection.rollback();
                logger.error("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                logger.error("Rollback failed: " + rollbackEx.getMessage());
            }

        } finally {

            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (Exception e) {
                logger.error("Failed to reset auto-commit: " + e.getMessage());
            }

        }
        return null;
    }
}
