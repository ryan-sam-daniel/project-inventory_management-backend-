package com.twozo.app.utility;

public class VendorQuery {
    public static final String SELECT_Vendor_BY_PHONE = 
        "Select id, name, phone_no, city, pincode from \"inventoryDB\".\"Vendor\" where phone_no = ?";
    // Query to insert a new customer
    public static final String INSERT_VENDOR = 
        "INSERT INTO \"inventoryDB\".\"Vendor\"(name, phone_no, city, pincode) VALUES (?, ?, ?, ?) ON CONFLICT (phone_no) DO NOTHING;";
}
