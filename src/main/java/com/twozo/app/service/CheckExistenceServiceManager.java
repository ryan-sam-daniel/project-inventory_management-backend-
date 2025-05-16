package com.twozo.app.service;

import com.twozo.app.model.Customer;
import com.twozo.app.model.Payment;
import com.twozo.app.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public interface CheckExistenceServiceManager {
    Vendor checkVendor(String phoneNo);
    Customer checkCustomer(String phoneNo);
    Payment checkExistence(int id);
}
