package com.twozo.app.service;

import com.twozo.app.dao.StakeHolderDbHandler;
import com.twozo.app.model.Customer;
import com.twozo.app.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public class StakeHolderAdderService implements StakeHolderAdderServiceManager {
    private final StakeHolderDbHandler<Customer> customerDbHandler;
    private final StakeHolderDbHandler<Vendor> vendorDbHandler;

    public StakeHolderAdderService(final StakeHolderDbHandler<Customer> customerDbHandler, final StakeHolderDbHandler<Vendor> vendorDbHandler){
        this.customerDbHandler = customerDbHandler;
        this.vendorDbHandler = vendorDbHandler;
    }

    @Override
    public int addCustomer(final Customer customer){
        return customerDbHandler.store(customer);
    }

    @Override
    public int addVendor(final Vendor vendor){
        return vendorDbHandler.store(vendor);
    }
}
