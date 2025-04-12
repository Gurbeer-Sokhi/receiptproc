package com.fetch.receiptproc.controller;

import com.fetch.receiptproc.model.Receipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fetch.receiptproc.service.receiptService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
@Slf4j
public class ReceiptController {

    public final receiptService receiptService;

    @Autowired
    public ReceiptController(com.fetch.receiptproc.service.receiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    @Validated
    public ResponseEntity<?> processRequest(@RequestBody(required = true)Receipt receipt){

            log.info("[ReceiptController] : [processRequest] Request Body = " + receipt);

            String id = receiptService.processRequest(receipt);
            Map<String,String> response = new HashMap<>();
            response.put("id",id);

            log.info("[ReceiptController] : [processRequest] Response = " + response);

            return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/points")
    @Validated
    public ResponseEntity<?> getPointsForReceipt(@PathVariable String id){

            log.info("[ReceiptController] : [getPointsForReceipt] id = " + id);

            Integer points = receiptService.getPointsAwarded(id);
            Map<String,Integer> response = new HashMap<>();
            response.put("points",points);

            log.info("[ReceiptController] : [processRequest] Response = " + response);

            return ResponseEntity.ok(response);
    }
}
