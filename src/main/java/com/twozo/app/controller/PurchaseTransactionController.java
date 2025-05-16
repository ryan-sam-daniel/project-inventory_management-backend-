package com.twozo.app.controller;

import com.twozo.app.model.PurchaseTransactionRequest;
import com.twozo.app.model.ReturnMsgDTO;
import com.twozo.app.service.TradeTransactionService;
import com.twozo.app.validator.PurchaseTransactionValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/purchase")
public class PurchaseTransactionController  {
    private final TradeTransactionService<PurchaseTransactionRequest> purchaseTransactionService;
    private final PurchaseTransactionValidator purchaseTransactionValidator;

    public PurchaseTransactionController (final TradeTransactionService<PurchaseTransactionRequest> purchaseTransactionService, final PurchaseTransactionValidator purchaseTransactionValidator){
        this.purchaseTransactionService = purchaseTransactionService;
        this.purchaseTransactionValidator = purchaseTransactionValidator;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> processPurchaseTransaction(@RequestBody final PurchaseTransactionRequest purchaseTransactionRequest) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationMsgs = purchaseTransactionValidator.validatePurchaseTransaction(purchaseTransactionRequest);

        if(!validationMsgs.isEmpty()){
            return ResponseEntity.badRequest().body(purchaseTransactionValidator.validatePurchaseTransaction(purchaseTransactionRequest));
        }

        if(purchaseTransactionService.completeTrade(purchaseTransactionRequest)) {
            returnMsgDTO.setMsg("Purchase transaction success. ");
            return ResponseEntity.ok(returnMsgDTO);
        } else{
            returnMsgDTO.setMsg("Purchase transaction Failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

}
