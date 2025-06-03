package com.twozo.inventorymanagementsystem.validator;

import com.twozo.inventorymanagementsystem.model.FinalAmountRequest;
import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.PurchaseTransactionRequest;
import com.twozo.inventorymanagementsystem.model.SaleTransactionRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class TradeValidator implements GeneralValidator<SaleTransactionRequest> {

    public Collection<String> validateTradeOperation(final Product product) {
        final Collection<String> validationMsgs = new ArrayList<>();

        if (product.getStockQuantity() <= 0) {
            validationMsgs.add("Quantity less than zero or negative");
            System.out.println(product.getStockQuantity());
        }

        if (product.getId() <= 0) {
            validationMsgs.add("Id less than zero or negative");
        }

        return validationMsgs;
    }

    public Collection<String> validateFinalAmountRequest(final FinalAmountRequest finalAmountRequest) {
        final Collection<String> validationMsgs = new ArrayList<>();

        if (finalAmountRequest.getAmount() <= 0) {
            validationMsgs.add("Amount is zero / negative");
        }

        if (finalAmountRequest.getRate() < 0 || finalAmountRequest.getRate() > 100) {
            validationMsgs.add("Discount Rate should be greater than or equal to zero");
        }

        return validationMsgs;
    }

    @Override
    public Collection<String> validate(final SaleTransactionRequest saleTransactionRequest) {
        final Collection<String> validationMsgs = new ArrayList<>();

        if (saleTransactionRequest.getMode().isEmpty()) {
            validationMsgs.add("Mode of Payment is Empty");
        }

        if (saleTransactionRequest.getCart() == null) {
            validationMsgs.add("Cart is null. ");
        }

        if (saleTransactionRequest.getSale() == null) {
            validationMsgs.add("Purchase details are null. ");
        }

        if (saleTransactionRequest.getAmount() <= 0) {
            validationMsgs.add("Amount is negative or zero");
        }

        if (saleTransactionRequest.getPhoneNo().isEmpty() || !saleTransactionRequest.getPhoneNo().matches("^[1-9][0-9]{9}$")) {
            validationMsgs.add("Phone number is wrong. ");
        }

        return validationMsgs;
    }

    @Override
    public String validateId(int id) {

        if (id <= 0) {
            return "Invalid id ! ID should not be negative";
        }

        return null;
    }

    @Override
    public String validatePhoneNo(String phoneNo) {
        throw new UnsupportedOperationException("this operation is not supported");
    }

    public Collection<String> validatePurchaseTransaction(final PurchaseTransactionRequest purchaseTransactionRequest) {
        final Collection<String> validationMsgs = new ArrayList<>();

        if (purchaseTransactionRequest.getMode().isEmpty()) {
            validationMsgs.add("Mode of Payment is Empty");
        }

        if (purchaseTransactionRequest.getCart() == null) {
            validationMsgs.add("Cart is null. ");
        }

        if (purchaseTransactionRequest.getPurchase() == null) {
            validationMsgs.add("Purchase details are null. ");
        }

        if (purchaseTransactionRequest.getAmount() <= 0) {
            validationMsgs.add("Amount is negative or zero");
        }

        if (purchaseTransactionRequest.getPhoneNo().isEmpty() && purchaseTransactionRequest.getPhoneNo().matches("^[0-9]{10}$")) {
            validationMsgs.add("Phone number is wrong. ");
        }

        return validationMsgs;
    }

}
