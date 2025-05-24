package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.Customer;
import com.twozo.inventorymanagementsystem.model.Payment;
import com.twozo.inventorymanagementsystem.model.ReturnMsgDTO;
import com.twozo.inventorymanagementsystem.model.Vendor;
import com.twozo.inventorymanagementsystem.service.CheckExistenceService;
import com.twozo.inventorymanagementsystem.validator.CustomerValidator;
import com.twozo.inventorymanagementsystem.validator.PaymentValidator;
import com.twozo.inventorymanagementsystem.validator.VendorValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/find")
public class CheckExistenceController {
    private final CheckExistenceService checkExistenceServiceImpl;
    private final CustomerValidator customerValidator;
    private final VendorValidator vendorValidator;
    private final PaymentValidator paymentValidator;

    public CheckExistenceController(final CheckExistenceService checkExistenceServiceImpl, final CustomerValidator customerValidator, final PaymentValidator paymentValidator, final VendorValidator vendorValidator){
        this.checkExistenceServiceImpl = checkExistenceServiceImpl;
        this.customerValidator = customerValidator;
        this.paymentValidator = paymentValidator;
        this.vendorValidator = vendorValidator;

    }

    @PostMapping("/customer")
 // ResponseEntity -  status code, headers, and body
     public ResponseEntity<?> findCustomerById(@RequestParam final String phoneNo){

        try{
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = customerValidator.validatePhoneNo(phoneNo);

            if (validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final boolean isAvailable = checkExistenceServiceImpl.checkCustomer(phoneNo);

            if (isAvailable) {
                returnMsgDTO.setMsg("Customer found");
                return ResponseEntity.ok(returnMsgDTO);
            } else{
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/vendor")
    public ResponseEntity<?> findVendorById(@RequestParam final String phoneNo){

        try {
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = vendorValidator.validatePhoneNo(phoneNo);

            if(validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final boolean isAvailable = checkExistenceServiceImpl.checkVendor(phoneNo);

            if (isAvailable) {
                returnMsgDTO.setMsg("Vendor found");
                return ResponseEntity.ok(returnMsgDTO);
            } else{
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> findPaymentById(@RequestParam final int id){

        try {
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = paymentValidator.validateId(id);

            if(validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final boolean isAvailable = checkExistenceServiceImpl.checkPayment(id);

            if (isAvailable) {
                returnMsgDTO.setMsg("Payment found");
                return ResponseEntity.ok(returnMsgDTO);
            } else{
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

}
