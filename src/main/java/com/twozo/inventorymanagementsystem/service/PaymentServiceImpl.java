package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.PaymentDao;
import com.twozo.inventorymanagementsystem.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDao<Payment> paymentDbHandler;

    public PaymentServiceImpl(final PaymentDao<Payment> paymentDbHandler) {
        this.paymentDbHandler = paymentDbHandler;
    }

    @Override
    public boolean exists(final int id) {
        return paymentDbHandler.exists(id);
    }

}
