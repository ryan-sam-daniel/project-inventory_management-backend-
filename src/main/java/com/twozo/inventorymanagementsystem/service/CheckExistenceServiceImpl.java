package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.CustomerDao;
import com.twozo.inventorymanagementsystem.dao.PaymentDao;
import com.twozo.inventorymanagementsystem.dao.VendorDao;
import com.twozo.inventorymanagementsystem.model.Customer;
import com.twozo.inventorymanagementsystem.model.Payment;
import com.twozo.inventorymanagementsystem.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {
    private final VendorDao<Vendor> vendorDbHandler;
    private final CustomerDao<Customer> customerDbHandler;
    private final PaymentDao<Payment> paymentDbHandler;

    public CheckExistenceServiceImpl(final VendorDao<Vendor> vendorDbHandler, final CustomerDao<Customer> customerDbHandler,
                                     final PaymentDao<Payment> paymentDbHandler){
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
