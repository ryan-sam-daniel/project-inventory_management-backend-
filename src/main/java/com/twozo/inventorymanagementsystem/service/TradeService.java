package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.model.CartUpdateMode;
import com.twozo.inventorymanagementsystem.model.FinalAmountRequest;
import com.twozo.inventorymanagementsystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface TradeService<S,T,U,V> {
    boolean add(Product product);
    boolean remove(Product product);
    boolean clearCart();
    Collection<U> getCart();
    U summate(S s);
    boolean updateCartItem(U u, S s, CartUpdateMode mode);
    double getSubtotal();
    double getTax();
    double getFinalAmount(FinalAmountRequest finalAmountRequest);
    boolean completeTrade(V transactionRequest);
}


