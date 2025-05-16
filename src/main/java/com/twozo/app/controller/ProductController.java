package com.twozo.app.controller;

import com.twozo.app.model.Product;
import com.twozo.app.model.ReturnMsgDTO;
import com.twozo.app.service.ProductServiceManager;
import com.twozo.app.validator.GeneralValidator;
import com.twozo.app.validator.ProductValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceManager productService;
    private final ProductValidator productValidator;
    private final GeneralValidator generalValidator;

    public ProductController(final ProductServiceManager productService,final ProductValidator productValidator, final GeneralValidator generalValidator){
        this.productService = productService;
        this.productValidator = productValidator;
        this.generalValidator = generalValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<ReturnMsgDTO> addProduct(@RequestBody final Product product){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationErrors = productValidator.validateAdding(product);

        if (validationErrors != null) {
            returnMsgDTO.setMsg("{\"error\":\"\n" + validationErrors.toString().trim() + "\"\n}");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final int id = productService.add(product);
        returnMsgDTO.setMsg("Product added successfully");
        return ResponseEntity.ok(returnMsgDTO);
    }

    @GetMapping("/inventory")
    public Collection<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/remove")
    public ResponseEntity<ReturnMsgDTO> removeProduct(@RequestParam final int id){
        final String validationMsg = generalValidator.validateId(id);
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

}
