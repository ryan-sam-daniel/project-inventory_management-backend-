package com.twozo.app.validator;

import com.twozo.app.model.FinalAmountRequest;
import com.twozo.app.model.TradeOperationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class GeneralValidator {
    public boolean validateStockQuantity(final int quantity){
        return quantity > 0;
    }

    public boolean validateRate(final double rate){
        return  (rate > 0 && rate <= 100);
    }

    public boolean validateAmount(final double amount){
        return amount>=0;
    }

    public String validatePhoneNo (final String phoneNo){

        if (phoneNo.length() != 10 || !phoneNo.matches("^[1-9][0-9]{9}$")){
            return "Invalid phone number";
        }

        return null;
    }

    public String validateId (final int id){

        if(id <= 0){
            return "Invalid id ! ID should not be negative";
        }

        return null;
    }

}
