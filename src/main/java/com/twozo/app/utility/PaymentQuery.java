package com.twozo.app.utility;

public class PaymentQuery {
    public static final
    String INSERT_PAYMENT = "Insert into \"inventoryDB\".\"Transaction\" (method, amount ,trnx_type, date) Values (?, ?, CAST(? AS \"inventoryDB\".transaction_type), ?)";

    public static final String FIND_PAYMENT_BY_ID = "select id, method, amount, trnx_type, date from \"inventoryDB\".\"Transaction\" where id = ?";
}

