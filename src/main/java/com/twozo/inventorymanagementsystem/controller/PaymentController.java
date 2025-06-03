package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.ReturnMsgDTO;
import com.twozo.inventorymanagementsystem.service.PaymentService;
import com.twozo.inventorymanagementsystem.validator.PaymentValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentValidator paymentValidator;

    public PaymentController(final PaymentService paymentService, final PaymentValidator paymentValidator) {
        this.paymentService = paymentService;
        this.paymentValidator = paymentValidator;
    }

    @PostMapping("/find")
    public ResponseEntity<?> findPaymentById(@RequestParam final int id) {

        try {
            final ReturnMsgDTO returnMsgDTO = new ReturnMsgDTO();
            final String validationMsg = paymentValidator.validateId(id);

            if (validationMsg != null) {
                returnMsgDTO.setMsg(validationMsg);
                return ResponseEntity.badRequest().body(returnMsgDTO);
            }

            final boolean isAvailable = paymentService.exists(id);

            if (isAvailable) {
                returnMsgDTO.setMsg("Payment found");
                return ResponseEntity.ok(returnMsgDTO);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

}
