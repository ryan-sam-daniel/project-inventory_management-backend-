package com.twozo.app.service;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface CartSummationService<T> {
    double getSubTotal(Collection<T> cart);
    double getTaxAmount(Collection<T> cart);
    double getFinalAmount(double amount, double rate);
}
