package com.twozo.app.validator;

import com.twozo.app.model.PurchaseTransactionRequest;
import com.twozo.app.model.SaleTransactionRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class SaleTransactionValidator {
    public Collection<String> validateSaleTransaction(final SaleTransactionRequest saleTransactionRequest){
        final Collection<String> validationMsgs = new ArrayList<>();

        if(saleTransactionRequest.getMode().isEmpty()){
            validationMsgs.add("Mode of Payment is Empty");
        }

        if (saleTransactionRequest.getCart() == null){
            validationMsgs.add("Cart is null. ");
        }

        if (saleTransactionRequest.getSale() == null){
            validationMsgs.add("Purchase details are null. ");
        }

        if (saleTransactionRequest.getAmount() <= 0){
            validationMsgs.add("Amount is negative or zero");
        }

        if (saleTransactionRequest.getPhoneNo().isEmpty() || !saleTransactionRequest.getPhoneNo().matches("^[1-9][0-9]{9}$")) {
            validationMsgs.add("Phone number is wrong. ");
        }

        return validationMsgs;
    }
}
