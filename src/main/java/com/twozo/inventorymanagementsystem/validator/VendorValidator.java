package com.twozo.inventorymanagementsystem.validator;

import com.twozo.inventorymanagementsystem.model.Vendor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class VendorValidator implements GeneralValidator<Vendor>{

    @Override
    public Collection<String> validate(final Vendor vendor){
        final Collection<String> errorMessages = new ArrayList<>();

        if(vendor.getPhoneNo().length() != 10){
            errorMessages.add("Length of phone should be exactly ten. ");
        }

        if(vendor.getName().isEmpty()){
            errorMessages.add("Customer name is empty. ");
        }

        if(vendor.getCity().isEmpty()){
            errorMessages.add("City name is empty. ");
        }

        if(vendor.getPincode() == 0 || String.valueOf(vendor.getPincode()).length() != 6){
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
