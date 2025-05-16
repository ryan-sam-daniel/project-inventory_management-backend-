package com.twozo.app.controller;

import com.twozo.app.model.ReturnMsgDTO;
import com.twozo.app.model.SaleTransactionRequest;
import com.twozo.app.service.TradeTransactionService;
import com.twozo.app.validator.SaleTransactionValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sale")
public class SaleTransactionController  {
    private final TradeTransactionService<SaleTransactionRequest> saleTransactionService;
    private final SaleTransactionValidator saleTransactionValidator;

    public SaleTransactionController (final TradeTransactionService<SaleTransactionRequest> saleTransactionService,final SaleTransactionValidator saleTransactionValidator){
        this.saleTransactionService = saleTransactionService;
        this.saleTransactionValidator = saleTransactionValidator;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> processSaleTransaction(@RequestBody final SaleTransactionRequest saleTransactionRequest) {
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        final Collection<String> validationMsgs = saleTransactionValidator.validateSaleTransaction(saleTransactionRequest);

        if(!validationMsgs.isEmpty()){
            return ResponseEntity.badRequest().body(saleTransactionValidator.validateSaleTransaction(saleTransactionRequest));
        }

        if(saleTransactionService.completeTrade(saleTransactionRequest)){
            returnMsgDTO.setMsg("Sale transaction success");
            return ResponseEntity.ok(returnMsgDTO);
        } else{
            returnMsgDTO.setMsg("Sale transaction Failed");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

    }

}
