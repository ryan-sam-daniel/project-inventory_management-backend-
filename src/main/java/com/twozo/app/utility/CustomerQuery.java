package com.twozo.app.utility;

public class CustomerQuery {
    // Query to find customer by phone number
    public static final String SELECT_CUSTOMER_BY_PHONE = "SELECT id, name, phone_no, city, pincode FROM \"inventoryDB\".\"Customer\" WHERE phone_no = ?";
    // Query to insert a new customer
    public static final String INSERT_CUSTOMER = "INSERT INTO \"inventoryDB\".\"Customer\"(name, phone_no, city, pincode) VALUES (?, ?, ?, ?) ;";
}
