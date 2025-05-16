package com.twozo.app.controller;

import com.twozo.app.model.Customer;
import com.twozo.app.model.Payment;
import com.twozo.app.model.ReturnMsgDTO;
import com.twozo.app.model.Vendor;
import com.twozo.app.service.CheckExistenceService;
import com.twozo.app.validator.GeneralValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/find")
public class CheckExistenceController {
    private final CheckExistenceService checkExistenceService;
    private final GeneralValidator generalValidator;


    public CheckExistenceController(final CheckExistenceService checkExistenceService, final GeneralValidator generalValidator){
        this.checkExistenceService = checkExistenceService;
        this.generalValidator = generalValidator;
    }

    @PostMapping("/customer")
 // ResponseEntity -  status code, headers, and body
     public ResponseEntity<?> findCustomerById(@RequestParam final String phoneNo){

        try{
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = generalValidator.validatePhoneNo(phoneNo);

            if (validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final Customer data = checkExistenceService.checkCustomer(phoneNo);
            final Optional<Customer> checkNull = Optional.ofNullable(data);
            return checkNull.isPresent() ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/vendor")
    public ResponseEntity<?> findVendorById(@RequestParam final String phoneNo){

        try {
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = generalValidator.validatePhoneNo(phoneNo);

            if(validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final Vendor data = checkExistenceService.checkVendor(phoneNo);
            final Optional<Vendor> checkNull = Optional.ofNullable(data);
            return checkNull.isPresent() ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> findPaymentById(@RequestParam final int id){

        try {
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = generalValidator.validateId(id);

            if(validationMsg != null){
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final Payment data = checkExistenceService.checkExistence(id);
            final Optional<Payment> checkNull = Optional.ofNullable(data);
            return checkNull.isPresent() ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

}
