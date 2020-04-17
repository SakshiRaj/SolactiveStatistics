package com.solactive.statistics.controllers;

import com.solactive.statistics.model.TransactionHistory;
import com.solactive.statistics.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
public class TransactionsController {

    private static final Long ONE_MINUTE_IN_MILLIS = 60 * 1000l;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<String> transaction(@RequestBody TransactionHistory transactionHistory){
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long epochInMillis = utc.toEpochSecond() * 1000;
        if(transactionHistory.getTxntime() < epochInMillis - ONE_MINUTE_IN_MILLIS){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            transactionService.add(transactionHistory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
