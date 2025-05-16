package com.twozo.app.dao;

import com.twozo.app.model.Payment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public interface TradeDbHandler<A,S,T>  extends CRUD<S>{
    boolean storeItem(List<T> cart,int id, Connection connection);
    boolean processTransaction(A a, Payment payment, S s, List<T> cart);
}
