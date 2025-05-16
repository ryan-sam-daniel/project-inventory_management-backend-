package com.twozo.app.controller;

import com.twozo.app.model.*;
import com.twozo.app.service.TradeService;
import com.twozo.app.validator.TradeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sale")
public class SaleController{
    private final TradeService<Product, Sale, SaleItem> saleService;
    private final TradeValidator tradeValidator;

    public SaleController(final TradeService<Product, Sale, SaleItem> saleService, final TradeValidator tradeValidator){
        this.saleService = saleService;
        this.tradeValidator = tradeValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<ReturnMsgDTO> add(@RequestBody  final TradeOperationDTO tradeOperationDTO){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(tradeOperationDTO).isEmpty()){
            returnMsgDTO.setMsg("Quantity is zero or less than zero");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        returnMsgDTO.setMsg(saleService.add(tradeOperationDTO));
        return ResponseEntity.ok(returnMsgDTO);
    }

    @PostMapping("/remove")
    public ResponseEntity<ReturnMsgDTO> remove(@RequestBody final TradeOperationDTO tradeOperationDTO){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();

        if(!tradeValidator.validateTradeOperation(tradeOperationDTO).isEmpty()){
            returnMsgDTO.setMsg("Quantity is zero or less than zero");
            return ResponseEntity.badRequest().body(returnMsgDTO);
        }

        returnMsgDTO.setMsg(saleService.remove(tradeOperationDTO));
        return ResponseEntity.ok(returnMsgDTO);
    }

    @GetMapping("/create")
    private ResponseEntity<ReturnMsgDTO> createNew(){
        final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
        returnMsgDTO.setMsg(saleService.createNew());
        return ResponseEntity.ok(returnMsgDTO);
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

}
