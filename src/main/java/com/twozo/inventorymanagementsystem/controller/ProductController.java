package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.Product;
import com.twozo.inventorymanagementsystem.model.ReturnMsgDTO;
import com.twozo.inventorymanagementsystem.service.ProductService;
import com.twozo.inventorymanagementsystem.validator.ProductValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductValidator productValidator;

    public ProductController(final ProductService productService, final ProductValidator productValidator){
        this.productService = productService;
        this.productValidator = productValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody final Product product){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationErrors = productValidator.validate(product);

        if (validationErrors != null) {
            returnMsgDTO.setMsg("{\"error\":\"\n" + validationErrors.toString().trim() + "\"\n}");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final int id = productService.add(product);

        if(id != -1){
            return ResponseEntity.ok(id);
        }

        returnMsgDTO.setMsg("Product added successfully");
        return ResponseEntity.badRequest().body(returnMsgDTO);
    }

    @GetMapping("/viewProducts")
    public Collection<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/remove")
    public ResponseEntity<ReturnMsgDTO> removeProduct(@RequestParam final int id){
        final String validationMsg = productValidator.validateId(id);
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(validationMsg != null){
            returnMsgDTO.setMsg(validationMsg);
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final boolean removed = productService.remove(id);

        if (removed) {
            returnMsgDTO.setMsg("Product Removed");
            return ResponseEntity.ok(returnMsgDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/update")
    public ResponseEntity<ReturnMsgDTO> update(@RequestBody final Product product){
        System.out.println(product);
        ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final boolean updated = productService.update(product);

        if (updated){
            returnMsgDTO.setMsg("Product updated successfully");
            return ResponseEntity.ok(returnMsgDTO);
        }
        else{
            returnMsgDTO.setMsg("Product Updation failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

}
