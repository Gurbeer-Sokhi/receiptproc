package com.fetch.receiptproc.service;

import com.fetch.receiptproc.model.Receipt;

public interface receiptService {

    String processRequest(Receipt receipt);
    Integer getPointsAwarded(String id);
}
