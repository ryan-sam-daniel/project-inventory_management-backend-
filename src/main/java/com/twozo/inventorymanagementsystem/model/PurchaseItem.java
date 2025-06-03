package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class PurchaseItem extends OrderItem<PurchaseItem> implements TradeItem {

    private int purchaseId;
    private int productId;
    private int quantity;
    private double purchasePrice;
    private double subTotal;
    private double taxAmount;
    private double finalDiscountedAmount;

    public PurchaseItem() {
        //no-args constructor
    }

    public PurchaseItem(final int productId, final int quantity, final double purchasePrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount) {
        this.productId = productId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    public PurchaseItem(final int purchaseId, final int productId, final int quantity, final double purchasePrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    @Override
    public double getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public double getSellingPrice() {
        return 0;
    }

    public void setPurchasePrice(final double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(final int id) {
        this.purchaseId = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(final double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public double getFinalDiscountedAmount() {
        return finalDiscountedAmount;
    }

    public void setFinalDiscountedAmount(final double finalDiscountedAmount) {
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

}
