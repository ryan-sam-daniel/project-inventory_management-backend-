package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class SaleItem extends OrderItem<SaleItem> implements TradeItem{
    private int saleId;
    private int productId ;
    private int quantity;
    private double sellingPrice;
    private double subTotal;
    private double taxAmount;
    private double finalDiscountedAmount;

    public SaleItem(final int productId, final int quantity, final double sellingPrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount){
        this.productId = productId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    public SaleItem(){
        //no-args constructor
    }

    public SaleItem(final int saleId,final int productId, final int quantity, final double sellingPrice, final double subTotal, final double taxAmount, final double finalDiscountedAmount){
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.finalDiscountedAmount = finalDiscountedAmount;
    
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(final int id) {
        this.saleId = id;
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

    @Override
    public double getPurchasePrice() {
        return 0;
    }

    public void setFinalDiscountedAmount(final double finalDiscountedAmount) {
        this.finalDiscountedAmount = finalDiscountedAmount;
    }

    @Override
    public double getSellingPrice() {
        return sellingPrice;
    }
    
    public void setSellingPrice(final double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

}
