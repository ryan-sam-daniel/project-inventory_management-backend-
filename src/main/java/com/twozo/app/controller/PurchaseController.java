package com.twozo.app.controller;

import com.twozo.app.model.*;
import com.twozo.app.service.TradeService;
import com.twozo.app.validator.TradeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/purchase")
public class PurchaseController{
    private final TradeService<Product, Purchase, PurchaseItem> purchaseService;
    private final TradeValidator tradeValidator;

    public PurchaseController(final TradeService<Product, Purchase, PurchaseItem> purchaseService, final TradeValidator tradeValidator){
        this.purchaseService = purchaseService;
        this.tradeValidator = tradeValidator;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody  final TradeOperationDTO tradeOperationDTO){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(tradeOperationDTO).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateTradeOperation(tradeOperationDTO));
        }

        returnMsgDTO.setMsg(purchaseService.add(tradeOperationDTO));
        return ResponseEntity.ok(returnMsgDTO);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody final TradeOperationDTO tradeOperationDTO){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(tradeOperationDTO).isEmpty()){
            return ResponseEntity.badRequest().body(tradeValidator.validateTradeOperation(tradeOperationDTO));
        }

        returnMsgDTO.setMsg(purchaseService.remove(tradeOperationDTO));
        return ResponseEntity.ok(returnMsgDTO);
    }

    @GetMapping("/create")
    public ResponseEntity<ReturnMsgDTO> createNew(){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        returnMsgDTO.setMsg(purchaseService.createNew());
        return ResponseEntity.ok(returnMsgDTO);
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

}
