package com.twozo.app.utility;

public class ReportQuery {
    public static String MAX_SALED_PRODUCT = "Select product.name from \"inventoryDB\".\"Product\" product inner join \"inventoryDB\".\"Sale_Item\"  sale_item on product.id = sale_item.product_id Group By product.name Order By sum(sale_item.quantity) DESC Limit 1 ;";
    public static String MIN_SALED_PRODUCT = "Select product.name from \"inventoryDB\".\"Product\" product inner join  \"inventoryDB\".\"Sale_Item\" sale_item on product.id = sale_item.product_id Group By product.name Order By sum(sale_item.quantity) ASC Limit 1 ;";
    public static String TOTAL_SALES_IN_DATE = "Select Sum(final_amount) from \"inventoryDB\".\"Sale_Order\" where sale_date >=  CURRENT_DATE - Interval \'7 DAY\';";
    public static String TOTAL_PURCHASE_IN_DATE = "Select Sum(final_amount) from \"inventoryDB\".\"Purchase_Order\" where purchase_date >=  CURRENT_DATE - Interval \'7 DAY\';";
    public static String STOCK_CHECK = "Select id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity  from \"inventoryDB\".\"Product\" where stock_quantity < 5 ;";
    public static String TOTAL_SALES = "Select Sum(final_amount) from \"inventoryDB\".\"Sale_Order\" ;";
    public static String TOTAL_PURCHASE = "Select Sum(final_amount) from \"inventoryDB\".\"Purchase_Order\" ;";
}





