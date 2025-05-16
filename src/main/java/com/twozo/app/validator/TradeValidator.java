package com.twozo.app.validator;

import com.twozo.app.model.FinalAmountRequest;
import com.twozo.app.model.TradeOperationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class TradeValidator {
    public Collection<String> validateTradeOperation(final TradeOperationDTO tradeOperationDTO){
        final Collection<String> validationMsgs = new ArrayList<>();

        if (tradeOperationDTO.getQuantity() <= 0){
            validationMsgs.add("Quantity less than zero or negative");
        }

        if (tradeOperationDTO.getId() <= 0){
            validationMsgs.add("Id less than zero or negative");
        }

        return validationMsgs;
    }

    public Collection<String> validateFinalAmountRequest(final FinalAmountRequest finalAmountRequest){
        final Collection<String> validationMsgs = new ArrayList<>();

        if(finalAmountRequest.getAmount() <= 0){
            validationMsgs.add("Amount is zero / negative");
        }

        if(finalAmountRequest.getRate() < 0 || finalAmountRequest.getRate() > 100){
            validationMsgs.add("Discount Rate should be greater than or equal to zero");
        }

        return validationMsgs;
    }
}
