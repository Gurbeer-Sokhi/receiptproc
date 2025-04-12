package com.fetch.receiptproc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

        String method = request.getMethod();
        String path = request.getRequestURI();

        if("POST".equalsIgnoreCase(method) && path.equals("/receipts/process")){
            return new ResponseEntity<>("The receipt is invalid.",HttpStatus.BAD_REQUEST);
        }
        else if("GET".equalsIgnoreCase(method) && path.matches("^/receipts/.+/points$")){
            return new ResponseEntity<>("No receipt found for that ID.",HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNotFound(NoSuchElementException ex,HttpServletRequest request){

        String method = request.getMethod();
        String path = request.getRequestURI();

        if("GET".equalsIgnoreCase(method) && path.matches("^/receipts/.+/points$")){
            return new ResponseEntity<>("No receipt found for that ID.",HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.badRequest().body("Not Found: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Something went wrong!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
