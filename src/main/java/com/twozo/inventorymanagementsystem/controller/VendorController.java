package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.ReturnMsgDTO;
import com.twozo.inventorymanagementsystem.model.Vendor;
import com.twozo.inventorymanagementsystem.service.VendorService;
import com.twozo.inventorymanagementsystem.validator.VendorValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/vendor")
@RestController
public class VendorController {
    private final VendorValidator vendorValidator;
    private final VendorService vendorService;

    public VendorController(final VendorValidator vendorValidator, final VendorService vendorService){
        this.vendorValidator = vendorValidator;
        this.vendorService = vendorService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVendor(@RequestBody final Vendor vendor){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> errorMessage = vendorValidator.validate(vendor);

        if(!errorMessage.isEmpty()){
            returnMsgDTO.setMsg("{\"error\":\"" + errorMessage.toString() + "\"}");
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        final int id = vendorService.addVendor(vendor);

        if(id != -1){
            return ResponseEntity.ok(id);
        } else{
            return ResponseEntity.internalServerError().build();
        }

    }


}
