package com.twozo.app.service;

import com.twozo.app.dao.TradeDbHandler;
import com.twozo.app.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SaleTransactionService implements TradeTransactionService<SaleTransactionRequest> {
    private final TradeDbHandler<Customer, Sale, SaleItem> saleDataHandler;
    private final CheckExistenceService checkExistenceService;
    private final ProductServiceManager productService;

    public SaleTransactionService(final TradeDbHandler<Customer, Sale, SaleItem> saleDataHandler, final ProductServiceManager productService, final CheckExistenceService checkExistenceService){
        this.saleDataHandler = saleDataHandler;
        this.productService = productService;
        this.checkExistenceService = checkExistenceService;
    }

    @Override
    public boolean completeTrade(final SaleTransactionRequest saleTransactionRequest) {
        final String phoneNo = saleTransactionRequest.getPhoneNo();
        final String method = saleTransactionRequest.getMode();
        final double amount = saleTransactionRequest.getAmount();
        final LocalDate currentDate = LocalDate.now();
        final Customer customer = checkExistenceService.checkCustomer(phoneNo);
        final Payment payment = new Payment(method, amount, "credit", currentDate);

        if(!saleDataHandler.processTransaction(customer,payment,saleTransactionRequest.getSale(),saleTransactionRequest.getCart())){
            return false;
        }
        else{
            return productService.removeStock(saleTransactionRequest.getCart());
        }

    }
}
