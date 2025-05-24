package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.*;
import com.twozo.inventorymanagementsystem.service.TradeService;
import com.twozo.inventorymanagementsystem.validator.TradeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sale")
public class SaleController{
    private final TradeService<Product, Sale, SaleItem, SaleTransactionRequest> saleService;
    private final TradeValidator tradeValidator;

    public SaleController(final TradeService<Product, Sale, SaleItem, SaleTransactionRequest> saleService, final TradeValidator tradeValidator){
        this.saleService = saleService;
        this.tradeValidator = tradeValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<ReturnMsgDTO> add(@RequestBody  final Product product){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(product).isEmpty()){
            returnMsgDTO.setMsg("Quantity is zero or less than zero");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        final boolean added = saleService.add(product);

        if (added){
            returnMsgDTO.setMsg("Product added to cart");
            return ResponseEntity.ok(returnMsgDTO);
        }
        else{
            returnMsgDTO.setMsg("Product not found in cart");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody final Product product) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if (!tradeValidator.validateTradeOperation(product).isEmpty()) {
            return ResponseEntity.badRequest().body(tradeValidator.validateTradeOperation(product));
        }

        final boolean removed = saleService.remove(product);

        if (removed) {
            returnMsgDTO.setMsg("Product removed from cart");
            return ResponseEntity.ok(returnMsgDTO);
        } else {
            returnMsgDTO.setMsg("Product not found in cart");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

    @GetMapping("/create")
    private ResponseEntity<ReturnMsgDTO> createNew(){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final boolean created = saleService.clearCart();

        if (created){
            returnMsgDTO.setMsg("New cart created");
            return ResponseEntity.ok(returnMsgDTO);
        }
        else{
            returnMsgDTO.setMsg("New cart creation failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

    @GetMapping("/getCart")
    private Collection<SaleItem> getCart(){
        return saleService.getCart();
    }

    @GetMapping("/subTotal")
    private double caluculateSubtotal(){
        return saleService.getSubtotal();
    }

    @GetMapping("/tax")
    private double calculateTax(){
        return saleService.getTax();
    }

    @PostMapping("/finalAmount")
    private ResponseEntity<?> calculateFinalAmount(@RequestBody final FinalAmountRequest finalAmountRequest){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if (!tradeValidator.validateFinalAmountRequest(finalAmountRequest).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateFinalAmountRequest(finalAmountRequest));
        }

        return ResponseEntity.ok(saleService.getFinalAmount(finalAmountRequest));
    }

    @PostMapping("/checkout/transaction")
    public ResponseEntity<?> processSaleTransaction(@RequestBody final SaleTransactionRequest saleTransactionRequest) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationMsgs = tradeValidator.validate(saleTransactionRequest);

        if(!validationMsgs.isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validate(saleTransactionRequest));
        }

        if(saleService.completeTrade(saleTransactionRequest)){
            returnMsgDTO.setMsg("Sale transaction success");
            return ResponseEntity.ok(returnMsgDTO);
        } else{
            returnMsgDTO.setMsg("Sale transaction Failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

}
