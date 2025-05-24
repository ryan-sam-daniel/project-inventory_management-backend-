package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.Report;
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
public class ReportDaoImpl implements ReportDao {
    private final DataSource dataSource;
    private final static Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);

    public ReportDaoImpl(final DataSource dataSource){
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
            final String maxSaledProduct = "Select product.name from \"inventoryDB\".\"Product\" product inner join \"inventoryDB\".\"Sale_Item\"  sale_item on product.id = sale_item.product_id Group By product.name Order By sum(sale_item.quantity) DESC Limit 1 ;";
            try (final PreparedStatement stmt = connection.prepareStatement(maxSaledProduct);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setMaxSold(resultSet.getString(1));
                }

            }

            // 2. Min Sold Product
            final String minSaledProduct = "Select product.name from \"inventoryDB\".\"Product\" product inner join  \"inventoryDB\".\"Sale_Item\" sale_item on product.id = sale_item.product_id Group By product.name Order By sum(sale_item.quantity) ASC Limit 1 ;";
            try (final PreparedStatement stmt = connection.prepareStatement(minSaledProduct);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setMinSold( resultSet.getString(1));
                }

            }

            // 3. Total Sales in Last 7 Days
            final String totalSalesInDate = "Select Sum(final_amount) from \"inventoryDB\".\"Sale_Order\" where sale_date >=  CURRENT_DATE - Interval \'7 DAY\';";
            try (final PreparedStatement stmt = connection.prepareStatement(totalSalesInDate);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setWeekSale(resultSet.getDouble(1));
                }

            }

            // 4. Total Purchase in Last 7 Days
            final String totalPurchaseInDate = "Select Sum(final_amount) from \"inventoryDB\".\"Purchase_Order\" where purchase_date >=  CURRENT_DATE - Interval \'7 DAY\';";
            try (final PreparedStatement stmt = connection.prepareStatement(totalPurchaseInDate);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setWeekPurchase(resultSet.getDouble(1));
                }

            }


            // 5. Stock Check
            final String stockCheck = "Select id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity  from \"inventoryDB\".\"Product\" where stock_quantity < 5 ;";
            try (final PreparedStatement stmt = connection.prepareStatement(stockCheck);
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
            final String totalSales = "Select Sum(final_amount) from \"inventoryDB\".\"Sale_Order\" ;";
            try (final PreparedStatement stmt = connection.prepareStatement(totalSales);
                 final ResultSet resultSet = stmt.executeQuery()) {

                if (resultSet.next()) {
                    report.setTotalSale(resultSet.getDouble(1));
                }

            }

            // 7. Total Purchase
            final String totalPurchase = "Select Sum(final_amount) from \"inventoryDB\".\"Purchase_Order\" ;";
            try (final PreparedStatement stmt = connection.prepareStatement(totalPurchase);
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
