package com.twozo.inventorymanagementsystem.dao;

public interface CustomerDao<T> extends WritableDao<T>, ReadableDao<T> {

    boolean exists(String phoneNo);

    int store(final T customer);
}
