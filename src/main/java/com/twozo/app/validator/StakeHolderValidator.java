package com.twozo.app.validator;

import com.twozo.app.model.Customer;
import com.twozo.app.model.Vendor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class StakeHolderValidator {
    public Collection<String> validateCustomer(final Customer customer){
        final Collection<String> errorMessages = new ArrayList<>();

        if(customer.getPhoneNo().length() != 10 || !customer.getPhoneNo().matches("^[6-9][0-9]{9}$")){
            errorMessages.add("Length of phone should be exactly ten. ");
        }

        if(customer.getName().isEmpty()){
            errorMessages.add("Customer name is empty. ");
        }

        if(customer.getCity().isEmpty()){
            errorMessages.add("City name is empty. ");
        }

        if(customer.getPincode() == 0 || String.valueOf(customer.getPincode()).length() != 6){
            errorMessages.add("Pincode is 0 or not satisfied due to count. ");
        }

        return errorMessages;
    }

    public Collection<String> validateVendor(final Vendor vendor){
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

}
