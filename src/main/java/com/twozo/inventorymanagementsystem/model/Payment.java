package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Payment {

    private int id;
    private String method;
    private double amount;
    private String type;
    private LocalDate date;

    public Payment(final String method, final double amount, final String type, final LocalDate date) {
        this.method = method;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Payment(final int id, final String method, final double amount, final String type, final LocalDate date) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Payment() {
        // Required by Jackson for deserialization
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

}
