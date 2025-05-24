package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.CustomerDao;
import com.twozo.inventorymanagementsystem.dao.PaymentDao;
import com.twozo.inventorymanagementsystem.dao.VendorDao;
import org.springframework.stereotype.Service;

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {
    private final VendorDao vendorDbHandler;
    private final CustomerDao customerDbHandler;
    private final PaymentDao paymentDbHandler;

    public CheckExistenceServiceImpl(final VendorDao vendorDbHandler, final CustomerDao customerDbHandler,
                                     final PaymentDao paymentDbHandler){
        this.vendorDbHandler = vendorDbHandler;
        this.customerDbHandler = customerDbHandler;
        this.paymentDbHandler = paymentDbHandler;
    }

    @Override
    public boolean checkVendor(final String phoneNo){
        return vendorDbHandler.checkExistence(phoneNo);
    }

    @Override
    public boolean checkCustomer(final String phoneNo) {
        return customerDbHandler.checkExistence(phoneNo);
    }

    @Override
    public boolean checkPayment(final int id){
        return paymentDbHandler.checkExistence(id);
    }
}
