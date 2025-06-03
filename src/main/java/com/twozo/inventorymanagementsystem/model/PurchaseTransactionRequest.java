package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseTransactionRequest {

    private String phoneNo;
    private String mode;
    private double amount;
    private List<PurchaseItem> cart;
    private Purchase purchase;

    public PurchaseTransactionRequest() {
        //no-args constructor
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public List<PurchaseItem> getCart() {
        return cart;
    }

    public void setCart(final List<PurchaseItem> cart) {
        this.cart = cart;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(final Purchase purchase) {
        this.purchase = purchase;
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

    public void setMode(final String method) {
        this.mode = method;
    }

}
