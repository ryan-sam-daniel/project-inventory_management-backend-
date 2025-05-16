package com.twozo.app.service;

import com.twozo.app.model.FinalAmountRequest;
import com.twozo.app.model.TradeOperationDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface TradeService<S,T,U> {
    String add(TradeOperationDTO tradeOperationDTO);
    String remove(TradeOperationDTO tradeOperationDTO);
    String createNew();
    Collection<U> getCart();
    U summate(S s);
    void updateCartItem(U u, S s, String mode);
    double getSubtotal();
    double getTax();
    double getFinalAmount(FinalAmountRequest finalAmountRequest);
}


