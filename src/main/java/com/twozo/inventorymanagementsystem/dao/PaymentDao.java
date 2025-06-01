package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao<T> extends WritableDao<T> {
    boolean checkExistence(int id);
    T get(int id);
}
