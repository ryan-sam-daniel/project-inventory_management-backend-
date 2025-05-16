package com.twozo.app.utility;

import org.springframework.stereotype.Component;

public class ProductQuery {
    // Query to fetch all products
    public static final String SELECT_ALL_PRODUCTS = 
        "SELECT id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity FROM \"inventoryDB\".\"Product\"";
    // Query to fetch a single product by id
    public static final String SELECT_PRODUCT_BY_ID = 
        "SELECT id, name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity FROM \"inventoryDB\".\"Product\" WHERE id = ?";
    // Query to insert a new product
    public static final String INSERT_PRODUCT = 
        "INSERT INTO \"inventoryDB\".\"Product\" (name, purchase_price, mrp, tax_rate, discount_rate, selling_price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
    // Query to delete a product by id
    public static final String DELETE_PRODUCT = 
        "DELETE FROM \"inventoryDB\".\"Product\" WHERE id = ?";
    // Query to update the stock of a product
    public static final String UPDATE_STOCK = 
        "UPDATE \"inventoryDB\".\"Product\" SET stock_quantity = (stock_quantity + ?) WHERE id = ?";
    // Query to remove stock from a product
    public static final String REMOVE_STOCK = 
        "UPDATE \"inventoryDB\".\"Product\" SET stock_quantity = (stock_quantity - ?) WHERE id = ?";
}
