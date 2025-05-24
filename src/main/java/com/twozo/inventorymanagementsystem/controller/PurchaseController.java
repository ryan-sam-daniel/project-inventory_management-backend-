package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.*;
import com.twozo.inventorymanagementsystem.service.TradeService;
import com.twozo.inventorymanagementsystem.validator.TradeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/purchase")
public class PurchaseController{
    private final TradeService<Product, Purchase, PurchaseItem, PurchaseTransactionRequest> purchaseService;
    private final TradeValidator tradeValidator;

    public PurchaseController(final TradeService<Product, Purchase, PurchaseItem, PurchaseTransactionRequest> purchaseService, final TradeValidator tradeValidator){
        this.purchaseService = purchaseService;
        this.tradeValidator = tradeValidator;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final Product product){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(product).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateTradeOperation(product));
        }

        final boolean added = purchaseService.add(product);

        if (added){
            returnMsgDTO.setMsg("Product added from cart");
            return ResponseEntity.ok(returnMsgDTO);
        }
        else{
            returnMsgDTO.setMsg("Product not found in cart");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody final Product product){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(product).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateTradeOperation(product));
        }

        final boolean removed = purchaseService.remove(product);

        if (removed){
            returnMsgDTO.setMsg("Product removed from cart");
            return ResponseEntity.ok(returnMsgDTO);
        }
        else{
            returnMsgDTO.setMsg("Product not found in cart");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

    @GetMapping("/clearCart")
    public ResponseEntity<ReturnMsgDTO> clearCart(){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final boolean created = purchaseService.clearCart();

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
    public Collection<PurchaseItem> getCart(){
        return purchaseService.getCart();
    }

    @GetMapping("/subTotal")
    public double caluculateSubtotal(){
        return purchaseService.getSubtotal();
    }

    @GetMapping("/tax")
    public double calculateTax(){
        return purchaseService.getTax();
    }

    @PostMapping("/finalAmount")
    public ResponseEntity<?> calculateFinalAmount(@RequestBody final FinalAmountRequest finalAmountRequest){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateFinalAmountRequest(finalAmountRequest).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateFinalAmountRequest(finalAmountRequest));
        }

        return ResponseEntity.ok(purchaseService.getFinalAmount(finalAmountRequest));
    }

    @PostMapping("/checkout/transaction")
    public ResponseEntity<?> processPurchaseTransaction(@RequestBody final PurchaseTransactionRequest purchaseTransactionRequest) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationMsgs = tradeValidator.validatePurchaseTransaction(purchaseTransactionRequest);

        if (!validationMsgs.isEmpty()) {
            return ResponseEntity.badRequest().body(tradeValidator.validatePurchaseTransaction(purchaseTransactionRequest));
        }

        if (purchaseService.completeTrade(purchaseTransactionRequest)) {
            returnMsgDTO.setMsg("Purchase transaction success. ");
            return ResponseEntity.ok(returnMsgDTO);
        } else {
            returnMsgDTO.setMsg("Purchase transaction Failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }
}
