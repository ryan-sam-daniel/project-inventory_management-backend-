package com.twozo.app.validator;

import com.twozo.app.model.PurchaseTransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PurchaseTransactionValidator {

    public Collection<String> validatePurchaseTransaction(final PurchaseTransactionRequest purchaseTransactionRequest){
        final Collection<String> validationMsgs = new ArrayList<>();

        if(purchaseTransactionRequest.getMode().isEmpty()){
            validationMsgs.add("Mode of Payment is Empty");
        }

        if (purchaseTransactionRequest.getCart()==null){
            validationMsgs.add("Cart is null. ");
        }

        if (purchaseTransactionRequest.getPurchase() == null){
            validationMsgs.add("Purchase details are null. ");
        }

        if (purchaseTransactionRequest.getAmount() <= 0){
            validationMsgs.add("Amount is negative or zero");
        }

        if (purchaseTransactionRequest.getPhoneNo().isEmpty() && purchaseTransactionRequest.getPhoneNo().matches("^[0-9]{10}$")) {
            validationMsgs.add("Phone number is wrong. ");
        }

        return validationMsgs;
    }
}
