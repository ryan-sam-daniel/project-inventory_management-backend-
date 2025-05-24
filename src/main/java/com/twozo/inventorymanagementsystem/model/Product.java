package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Product {
    private int id;
    private String name;
    private double purchasePrice;
    private double mrp;
    private int taxPercentage;
    private double sellingPrice;
    private double discountPercentage;
    private int stockQuantity;

    public Product(){
        //no-args constructor
    }

    public Product(final String name, final double purchasePrice, final double mrp, final int taxPercentage,
                    final double discountPercentage,final double sellingPrice, final int stockQuantity) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.mrp = mrp;
        this.taxPercentage = taxPercentage;
        this.discountPercentage = discountPercentage;
        this.sellingPrice = sellingPrice;
        this.stockQuantity = stockQuantity;
    }

    public Product(final int id,final String name, final double purchasePrice, final double mrp, final int taxPercentage,final double discountPercentage,
                final double sellingPrice,  final int stockQuantity) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.mrp = mrp;
        this.taxPercentage = taxPercentage;
        this.discountPercentage = discountPercentage;
        this.sellingPrice = sellingPrice;
        this.stockQuantity = stockQuantity;
    }

    public Product(final int id, final int stockQuantity){
        this.id = id;
        this.stockQuantity = stockQuantity;
    }


    public int getId() {
        return id;
    }

    //This method is for display the product
    @Override
    public String toString() {
        return "Product: " + name + ", Quantity: " + stockQuantity;
    }

    public String getName(){
        return name;
    }

    public double getSellingPrice(){
        return sellingPrice;
    }

    public int getStockQuantity(){
        return stockQuantity;
    }

    public double getPurchasePrice(){
        return purchasePrice;
    }

    public double getMrp(){
        return mrp;
    }

    public int getTaxPercentage(){
        return taxPercentage;
    }

    public double getDiscountPercentage(){
        return discountPercentage;
    }

    public void setStockQuantity(final int quantity){
        this.stockQuantity=quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
