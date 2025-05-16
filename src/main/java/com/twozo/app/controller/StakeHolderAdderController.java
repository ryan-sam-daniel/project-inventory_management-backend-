package com.twozo.app.controller;

import com.twozo.app.model.Customer;
import com.twozo.app.model.ReturnMsgDTO;
import com.twozo.app.model.Vendor;
import com.twozo.app.service.StakeHolderAdderServiceManager;
import com.twozo.app.validator.StakeHolderValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/stakeholder/add")
public class StakeHolderAdderController {
    private final StakeHolderAdderServiceManager stakeHolderAdderService;
    private final StakeHolderValidator stakeHolderValidator;

    public StakeHolderAdderController(final StakeHolderAdderServiceManager stakeHolderAdderService,final StakeHolderValidator stakeHolderValidator){
        this.stakeHolderAdderService = stakeHolderAdderService;
        this.stakeHolderValidator = stakeHolderValidator;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> addCustomer(@RequestBody final Customer customer){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> errorMessage = stakeHolderValidator.validateCustomer(customer);

        if (!errorMessage.isEmpty()) {
            returnMsgDTO.setMsg("{\"error\":\"" + errorMessage.toString().trim() + "\"}");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final int id = stakeHolderAdderService.addCustomer(customer);

        if(id != -1){
            return ResponseEntity.ok(id);
        } else{
            returnMsgDTO.setMsg("Customer with this phone number already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(returnMsgDTO);
        }

    }

    @PostMapping("/vendor")
    public ResponseEntity<?> addVendor(@RequestBody final Vendor vendor){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> errorMessage = stakeHolderValidator.validateVendor(vendor);

        if(!errorMessage.isEmpty()){
            returnMsgDTO.setMsg("{\"error\":\"" + errorMessage.toString() + "\"}");
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        final int id = stakeHolderAdderService.addVendor(vendor);

        if(id != -1){
            return ResponseEntity.ok(id);
        } else{
            return ResponseEntity.internalServerError().build();
        }

    }

}
