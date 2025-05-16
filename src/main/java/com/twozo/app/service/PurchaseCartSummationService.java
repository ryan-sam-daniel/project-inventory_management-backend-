package com.twozo.app.service;

import com.twozo.app.model.PurchaseItem;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PurchaseCartSummationService implements CartSummationService<PurchaseItem> {

    @Override
    public double getSubTotal(final Collection<PurchaseItem> cart){
        double subTotal = 0;

        for (final PurchaseItem item : cart) {
            final int quantity = item.getQuantity();
            final double price = item.getPurchasePrice();
            subTotal += (quantity * price);
        }

        return subTotal;
    }

    @Override
    public double getTaxAmount(final Collection<PurchaseItem> cart){
        double taxAmount = 0;

        for (final PurchaseItem item : cart) {
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
