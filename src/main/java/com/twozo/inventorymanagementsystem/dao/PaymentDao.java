package com.twozo.inventorymanagementsystem.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao<T> extends WritableDao<T> {

    boolean exists(int id);

    T get(int id);
}
