package com.twozo.app.utility;

public class SaleQuery {
    public static final String INSERT_SALE = "Insert into \"inventoryDB\".\"Sale_Order\" (customer_id, transaction_id,sale_date, sub_total, final_amount) values (?, ?, ?, ?, ?)";
    public static final String INSERT_SALE_ITEM = "Insert into \"inventoryDB\".\"Sale_Item\" (sale_order_id,product_id,quantity,selling_price,sub_total,tax_amount,final_amount) values (?, ?, ?, ?, ?, ?, ?)";
}
