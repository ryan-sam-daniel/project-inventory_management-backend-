package com.twozo.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

@Component
public class PurchaseItem{
    private int purchaseId;
    private int productId ;
    private int quantity;
    private double purchasePrice;
    private double subTotal;
    private double taxAmount;
    private double finalDiscountedAmount;

    public PurchaseItem(){
        //no-args constructor
    }

    public PurchaseItem(final int productId, final int quantity, final double purchasePrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount){
        this.productId = productId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    public PurchaseItem(final int purchaseId,final int productId, final int quantity, final double purchasePrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount){
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(final double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getFinalDiscountedAmount() {
        return finalDiscountedAmount;
    }

    public void setFinalDiscountedAmount(final double finalDiscountedAmount) {
        this.finalDiscountedAmount = finalDiscountedAmount;
    }
}
