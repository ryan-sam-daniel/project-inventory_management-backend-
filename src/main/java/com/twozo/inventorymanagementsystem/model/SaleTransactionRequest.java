package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleTransactionRequest {

    private String phoneNo;
    private String mode;
    private double amount;
    private List<SaleItem> cart;
    private Sale sale;

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public List<SaleItem> getCart() {
        return cart;
    }

    public void setCart(final List<SaleItem> cart) {
        this.cart = cart;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(final Sale sale) {
        this.sale = sale;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }
}
