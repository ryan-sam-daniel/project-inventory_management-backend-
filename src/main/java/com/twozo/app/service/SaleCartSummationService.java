package com.twozo.app.service;

import com.twozo.app.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SaleCartSummationService implements CartSummationService<SaleItem> {
    
    @Override
    public double getSubTotal(final Collection<SaleItem> cart){
        double subTotal = 0;

        for (final SaleItem item : cart) {
            final int quantity = item.getQuantity();
            final double price = item.getSellingPrice();
            subTotal += (quantity * price);
        }

        return subTotal;
    }

    @Override
    public double getTaxAmount(final Collection<SaleItem> cart){
        double taxAmount = 0;

        for (final SaleItem item : cart) {
            final double taxPrice = item.getTaxAmount();
            taxAmount += taxPrice;
        }

        return taxAmount;
    }

    @Override
    public double getFinalAmount(final double amount, final double rate){
        return (amount - ((amount*rate)/100));
    }
}
