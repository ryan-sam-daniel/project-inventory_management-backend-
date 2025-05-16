package com.twozo.app.service;

import com.twozo.app.dao.TradeDbHandler;
import com.twozo.app.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PurchaseTransactionService implements TradeTransactionService<PurchaseTransactionRequest> {
    private final TradeDbHandler<Vendor, Purchase, PurchaseItem> purchaseDataHandler;
    private final ProductServiceManager productService;
    private final CheckExistenceService checkExistenceService;

    public PurchaseTransactionService(final TradeDbHandler<Vendor, Purchase, PurchaseItem> purchaseDataHandler, final ProductServiceManager productService, final CheckExistenceService checkExistenceService){
        this.purchaseDataHandler = purchaseDataHandler;
        this.productService = productService;
        this.checkExistenceService = checkExistenceService;
    }

    @Override
    public boolean completeTrade(final PurchaseTransactionRequest purchaseTransactionRequest) {
        final String phoneNo = purchaseTransactionRequest.getPhoneNo();
        final String method = purchaseTransactionRequest.getMode();
        final double amount = purchaseTransactionRequest.getAmount();
        final LocalDate currentDate = LocalDate.now();
        final Vendor vendor = checkExistenceService.checkVendor(phoneNo);
        final Payment payment = new Payment(method, amount, "debit", currentDate);

        if(!purchaseDataHandler.processTransaction(vendor,payment,purchaseTransactionRequest.getPurchase(),purchaseTransactionRequest.getCart())){
            return false;
        }
        else{
            return productService.updateStock(purchaseTransactionRequest.getCart());
        }

    }

}
