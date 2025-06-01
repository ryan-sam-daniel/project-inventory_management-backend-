package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.VendorDao;
import com.twozo.inventorymanagementsystem.model.Vendor;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService{
    private final VendorDao<Vendor> vendorDbHandler;

    public VendorServiceImpl(final VendorDao<Vendor> vendorDbHandler){
        this.vendorDbHandler = vendorDbHandler;
    }

    @Override
    public int addVendor(final Vendor vendor){
        return vendorDbHandler.store(vendor);
    }

    @Override
    public Vendor get(String phoneNo) {
        return vendorDbHandler.get(phoneNo);
    }
}
