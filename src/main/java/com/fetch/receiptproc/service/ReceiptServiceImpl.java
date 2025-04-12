package com.fetch.receiptproc.service;

import com.fetch.receiptproc.model.Item;
import com.fetch.receiptproc.model.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptServiceImpl implements receiptService{

    private final Map<String, Integer> receiptPointsMap = new HashMap<>();

    @Override
    public String processRequest(Receipt receipt) {

            log.info("[ReceiptServiceImpl] : [processRequest] Receipt = " + receipt);
            String id = UUID.randomUUID().toString();
            int points = calculatePoints(receipt);
            receiptPointsMap.put(id,points);
            log.info("[ReceiptServiceImpl] : [processRequest] Points = " + points);
            return id;

    }

    @Override
    public Integer getPointsAwarded(String id) throws NoSuchElementException {

        log.info("[ReceiptServiceImpl] : [getPointsAwarded] Id = " + id);
        if(!receiptPointsMap.containsKey(id)){
            throw new NoSuchElementException("No receipt found for that ID");
        }
        int points = receiptPointsMap.get(id);
        log.info("[ReceiptServiceImpl] : [getPointsAwarded] Points = " + points );
        return points;
    }

    public int calculatePoints(Receipt receipt){

        log.info("[ReceiptServiceImpl] : [calculatePoints] Receipt = " + receipt);
        int totalPoints = 0;

        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points initially = " + totalPoints);
        totalPoints += calPointsOnTotal(receipt);

        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points after calculating on Total = " + totalPoints);

        totalPoints += calPointsOnItems(receipt);
        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points after calculating on Items = " + totalPoints);

        totalPoints += calPointsOnDate(receipt);
        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points after calculating on Date = " + totalPoints);

        totalPoints += calPointsOnTime(receipt);
        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points after calculating on Time = " + totalPoints);

        log.info("[ReceiptServiceImpl] : [calculatePoints] Total Points final = " + totalPoints);
        return totalPoints;
    }

    private int calPointsOnTime(Receipt receipt) {

        String[] time = receipt.getPurchaseTime().split(":");
        if(Integer.parseInt(time[0]) >= 14 && Integer.parseInt(time[0]) < 17){
            return 10;
        }
        return 0;
    }

    private int calPointsOnDate(Receipt receipt) {

        String[] date = receipt.getPurchaseDate().split("-");
        if(Integer.parseInt(date[2]) % 2 != 0){
            return 6;
        }
        return 0;
    }

    private int calPointsOnItems(Receipt receipt) {
        int points = 0;

        for(Item i : receipt.getItems()){
            if(i.getShortDescription().trim().length() % 3 == 0){
                log.info("[ReceiptServiceImpl] : [calPointsOnItems] short description length = " + i.getShortDescription().trim().length());
              double count = Double.parseDouble(i.getPrice());
              double calculatePoints = count * 0.2;
              int roundedPoints = (int) Math.ceil(calculatePoints);
              points += roundedPoints;
            }
        }


        int numberOfItems = receipt.getItems().size() / 2;
        points += (5 * numberOfItems);

        return points;
    }

    public int calPointsOnTotal(Receipt receipt){

        int count = 0;
        for(char c : receipt.getRetailer().toCharArray()){
            if(Character.isLetterOrDigit(c)){
                count ++;
            }
        }
        log.info("[ReceiptServiceImpl] : [calPointsTotal] Points for alphanumeric character = " + count);

        if(receipt.getTotal().matches("\\d+\\.00")){
            count += 50;
            log.info("[ReceiptServiceImpl] : [calPointsTotal] Total is a round dollar");
        }

        double total = Double.parseDouble(receipt.getTotal());

        if(total % 0.25 == 0){
            count += 25;
            log.info("[ReceiptServiceImpl] : [calPointsTotal] Total is a multiple of 0.25");
        }

        return count;
    }

}
