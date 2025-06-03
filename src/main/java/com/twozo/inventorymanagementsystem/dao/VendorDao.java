package com.twozo.inventorymanagementsystem.dao;

public interface VendorDao<T> extends WritableDao<T>, ReadableDao<T> {

    boolean exists(String phoneNo);

    int store(T vendor);
}
