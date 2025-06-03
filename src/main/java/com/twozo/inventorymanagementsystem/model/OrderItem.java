package com.twozo.inventorymanagementsystem.model;

import java.util.Collection;

public abstract class OrderItem<T extends TradeItem> {

    public double getCartSubtotal(final Collection<T> cart, final String mode) {
        double subTotal = 0;

        if (mode.equalsIgnoreCase("sale")) {

            for (final T item : cart) {
                final int quantity = item.getQuantity();
                final double price = item.getSellingPrice();
                subTotal += (quantity * price);
            }

        } else {

            for (final T item : cart) {
                final int quantity = item.getQuantity();
                final double price = item.getPurchasePrice();
                subTotal += (quantity * price);
            }

        }

        return subTotal;
    }

    public double getCartTaxAmount(final Collection<T> cart) {
        double taxAmount = 0;

        for (final T item : cart) {
            final double taxPrice = item.getTaxAmount();
            taxAmount += taxPrice;
        }

        return taxAmount;
    }

    public double getCartFinalAmount(final double amount, final double rate) {
        return (amount - ((amount * rate) / 100));
    }

}
