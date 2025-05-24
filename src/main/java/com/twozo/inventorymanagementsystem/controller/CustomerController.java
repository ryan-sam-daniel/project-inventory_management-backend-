package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.Customer;
import com.twozo.inventorymanagementsystem.model.ReturnMsgDTO;
import com.twozo.inventorymanagementsystem.service.CustomerService;
import com.twozo.inventorymanagementsystem.validator.CustomerValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerValidator customerValidator;
    private final CustomerService customerService;

    public CustomerController(final CustomerValidator customerValidator, final CustomerService customerService){
        this.customerValidator = customerValidator;
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody final Customer customer) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> errorMessage = customerValidator.validate(customer);

        if (!errorMessage.isEmpty()) {
            returnMsgDTO.setMsg("{\"error\":\"" + errorMessage.toString().trim() + "\"}");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final int id = customerService.addCustomer(customer);

        if (id != -1) {
            return ResponseEntity.ok(id);
        } else {
            returnMsgDTO.setMsg("Customer with this phone number already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(returnMsgDTO);
        }

    }
}
