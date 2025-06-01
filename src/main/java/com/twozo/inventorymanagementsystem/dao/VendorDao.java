package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Vendor;

public interface VendorDao<T> extends WritableDao<T> , ReadableDao<T> {
    boolean checkExistence(String phoneNo);
    int store(T vendor);
}
