package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class FinalAmountRequest {
    private double amount;
    private double rate;

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    public FinalAmountRequest() {
    }
}
