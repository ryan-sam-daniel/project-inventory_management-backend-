package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Customer;

public interface CustomerDao<T> extends WritableDao<T>, ReadableDao<T> {
    boolean checkExistence(String phoneNo);
    int store(final T customer);
}
