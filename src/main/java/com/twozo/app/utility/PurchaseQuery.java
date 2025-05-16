package com.twozo.app.utility;

public class PurchaseQuery {
    
    public static final String INSERT_PURCHASE = "Insert into \"inventoryDB\".\"Purchase_Order\" (vendor_id, transaction_id, purchase_date, sub_total, final_amount) values (?, ?, ?, ?, ?)";
    public static final String INSERT_PURCHASE_ITEM = "Insert into \"inventoryDB\".\"Purchase_Item\" (purchase_order_id,product_id,quantity,selling_price,sub_total,tax_amount,final_amount) values (?, ?, ?, ?, ?, ?, ?)";
}
