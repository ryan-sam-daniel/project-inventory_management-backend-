package com.twozo.inventorymanagementsystem.model;

public interface TradeItem {
    int getQuantity() ;

    double getSubTotal() ;

    double getTaxAmount() ;

    double getFinalDiscountedAmount() ;

    double getPurchasePrice();

    double getSellingPrice();
}
