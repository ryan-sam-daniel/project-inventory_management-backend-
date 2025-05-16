package com.twozo.app.service;

import com.twozo.app.model.Customer;
import com.twozo.app.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public interface StakeHolderAdderServiceManager {
    int addCustomer(Customer customer);
    int addVendor(Vendor vendor);
}
