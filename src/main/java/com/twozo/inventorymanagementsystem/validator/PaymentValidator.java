package com.twozo.inventorymanagementsystem.validator;

import com.twozo.inventorymanagementsystem.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PaymentValidator implements GeneralValidator<Payment>{

    @Override
    public Collection<String> validate(Payment payment) {
        return List.of();
    }

    @Override
    public String validateId (final int id){

        if(id <= 0){
            return "Invalid id ! ID should not be negative";
        }

        return null;
    }

    @Override
    public String validatePhoneNo(String phoneNo) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

}
