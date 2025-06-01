package com.twozo.inventorymanagementsystem.validator;

import com.twozo.inventorymanagementsystem.model.Customer;
import org.apache.coyote.ProtocolHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomerValidator implements GeneralValidator<Customer>{

    @Override
    public Collection<String> validate(final Customer customer){
        final Collection<String> errorMessages = new ArrayList<>();

        if(validatePhoneNo(customer.getPhoneNo()) != null){
            errorMessages.add("Length of phone should be exactly ten. ");
        }

        if(!validateName(customer.getName())){
            errorMessages.add("Customer name is empty. ");
        }

        if(!validateCity(customer.getCity())){
            errorMessages.add("City name is empty. ");
        }

        if(!validatePincode(customer.getPincode())){
            errorMessages.add("Pincode is 0 or not satisfied due to count. ");
        }

        return errorMessages;
    }

    @Override
    public String validateId(int id) {

        if(id <= 0){
            return "Invalid id ! ID should not be negative";
        }

        return null;
    }

    @Override
    public String validatePhoneNo (final String phoneNo){

        if (phoneNo.length() != 10 || !phoneNo.matches("^[1-9][0-9]{9}$")){
            return "Invalid phone number";
        }

        return null;
    }

    private boolean validateName (final String name){

        if (name.isEmpty()){
            return false;
        }

        return true;
    }

    private boolean validateCity (final String city){

        if (city.isEmpty()){
            return false;
        }

        return true;
    }

    private boolean validatePincode (final int pincode){

        if(pincode == 0 || String.valueOf(pincode).length() != 6){
            return false;
        }

        return true;
    }
}
