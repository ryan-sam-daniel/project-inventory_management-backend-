package com.twozo.app.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Report {
    private String maxSold;
    private String minSold;
    private double weekSale;
    private double weekPurchase;
    private HashMap<Integer,Product> lowStockItems;
    private double totalSale;
    private double totalPurchase;

    public Report(){
        //no-args constructor
    }

    public Report(final String maxSold, final String minSold, final double weekSale, final double weekPurchase, final HashMap<Integer, Product> lowStockItems, final double totalSale, final double totalPurchase){
        this.maxSold = maxSold;
        this.minSold = minSold;
        this.weekSale = weekSale;
        this.weekPurchase = weekPurchase;
        this.lowStockItems = lowStockItems;
        this.totalSale = totalSale;
        this.totalPurchase = totalPurchase;
    }

    public String getMaxSold() {
        return maxSold;
    }

    public void setMaxSold(final String maxSold) {
        this.maxSold = maxSold;
    }

    public HashMap<Integer,Product> getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(final HashMap<Integer, Product> lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public String getMinSold() {
        return minSold;
    }

    public void setMinSold(final String minSold) {
        this.minSold = minSold;
    }

    public double getWeekSale() {
        return weekSale;
    }

    public void setWeekSale(final double weekSale) {
        this.weekSale = weekSale;
    }

    public double getWeekPurchase() {
        return weekPurchase;
    }

    public void setWeekPurchase(final double weekPurchase) {
        this.weekPurchase = weekPurchase;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(final double totalSale) {
        this.totalSale = totalSale;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(final double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

}
