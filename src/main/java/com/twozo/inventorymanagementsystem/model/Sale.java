package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Sale {
    private int saleId;
    private int customerId;
    private int paymentId;
    private LocalDate billDate;
    private double subTotal;
    private double finalAmount;
    private List<SaleItem> saleItem;

    public Sale(){
        //no-args constructor
    }
    
    public Sale ( final int customerId, final int paymentId, final LocalDate billDate, final double subTotal, final double finalAmount, final List<SaleItem> saleItem){
        this.customerId = customerId;
        this.paymentId = paymentId;
        this.billDate = billDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.saleItem = saleItem;
    }

    public Sale (final int saleId, final int customerId, final int paymentId, final LocalDate billDate, final double subTotal, final double finalAmount, final List<SaleItem> saleItem){
        this.saleId = saleId;
        this.customerId = customerId;
        this.paymentId = paymentId;
        this.billDate = billDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.saleItem = saleItem;
    }

    public Sale (final LocalDate billDate, final double subTotal, final double finalAmount, final List<SaleItem> saleItem){
        this.billDate = billDate;
        this.subTotal = subTotal;
        this.finalAmount = finalAmount;
        this.saleItem = saleItem;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(final int saleId) {
        this.saleId = saleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final int customerId) {
        this.customerId = customerId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final int paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(final LocalDate billDate) {
        this.billDate = billDate;
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

    public List<SaleItem> getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(final List<SaleItem> saleItem) {
        this.saleItem = saleItem;
    }
}
