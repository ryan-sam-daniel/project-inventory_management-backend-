package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Purchase {
    private int purchaseId;
    private int vendorId;
    private int paymentId;
    private LocalDate purchaseDate;
    private double subTotal;
    private double finalAmount;
    private List<PurchaseItem> purchaseItem;

    public Purchase(){
        //no-args constructor
    }

    public Purchase (final int purchaseId, final int vendoreId, final int paymentId, final LocalDate purchaseDate, final double subTotal, final double finalAmount, final List<PurchaseItem> purchaseItem){
        this.purchaseId = purchaseId;
        this.vendorId = vendoreId;
        this.paymentId = paymentId;
        this.purchaseDate = purchaseDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.purchaseItem = purchaseItem;
    }

    public Purchase (final int vendoreId, final int paymentId, final LocalDate purchaseDate, final double subTotal, final double finalAmount, final List<PurchaseItem> purchaseItem){
        this.vendorId = vendoreId;
        this.paymentId = paymentId;
        this.purchaseDate = purchaseDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.purchaseItem = purchaseItem;
    }

    public Purchase (final LocalDate purchaseDate, final double subTotal, final double finalAmount, final List<PurchaseItem> purchaseItem){
        this.purchaseDate = purchaseDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.purchaseItem = purchaseItem;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(final int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(final int vendorId) {
        this.vendorId = vendorId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final int paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(final LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(final double subTotal) {
        this.subTotal = subTotal;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(final double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public List<PurchaseItem> getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(final List<PurchaseItem> purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

}
