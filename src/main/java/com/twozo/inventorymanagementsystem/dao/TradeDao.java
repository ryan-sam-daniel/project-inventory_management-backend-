package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Payment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public interface TradeDao<A,S,T>  extends WritableDao<S> {
    boolean storeItem(List<T> cart,int id, Connection connection);
    boolean processTransaction(A a, Payment payment, S s, List<T> cart);
}
