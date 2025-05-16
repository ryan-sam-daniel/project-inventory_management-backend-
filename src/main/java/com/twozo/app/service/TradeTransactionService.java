package com.twozo.app.service;

import org.springframework.stereotype.Service;

@Service
public interface TradeTransactionService<T> {
    boolean completeTrade(T transactionRequest);
}
