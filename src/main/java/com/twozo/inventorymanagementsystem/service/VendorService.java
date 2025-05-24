package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.model.Vendor;

public interface VendorService {
    int addVendor(Vendor vendor);
    Vendor get(String phoneNo);
}
