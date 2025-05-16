package com.twozo.app.service;

import com.twozo.app.dao.PaymentDbManager;
import com.twozo.app.dao.StakeHolderDbHandler;
import com.twozo.app.model.Customer;
import com.twozo.app.model.Payment;
import com.twozo.app.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public class CheckExistenceService implements CheckExistenceServiceManager {
    private final StakeHolderDbHandler<Vendor> vendorDbHandler;
    private final StakeHolderDbHandler<Customer> customerDbHandler;
    private final PaymentDbManager paymentDbHandler;

    public CheckExistenceService(final StakeHolderDbHandler<Vendor> vendorDbHandler, final StakeHolderDbHandler<Customer> customerDbHandler,
                                 final PaymentDbManager paymentDbHandler){
        this.vendorDbHandler = vendorDbHandler;
        this.customerDbHandler = customerDbHandler;
        this.paymentDbHandler = paymentDbHandler;
    }

    @Override
    public Vendor checkVendor(final String phoneNo){
        return vendorDbHandler.checkExistence(phoneNo);
    }

    @Override
    public Customer checkCustomer(final String phoneNo) {
        return customerDbHandler.checkExistence(phoneNo);
    }

    @Override
    public Payment checkExistence(final int id){
        return paymentDbHandler.checkExistence(id);
    }
}
