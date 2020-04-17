package com.solactive.statistics.controllers;

import com.solactive.statistics.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class StatisticsController {

    @Autowired
    TransactionService transactionService;

   @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Map<String, Number> statistics(){
       CompletableFuture<Map<String, Number>> statisticsFuture = transactionService.getStatistics();
       try {
           return statisticsFuture.get();
       } catch (InterruptedException | ExecutionException e) {
           e.getMessage();
       }
       return null;
   }

    @RequestMapping(value = "/statistics/{instrumentId}", method = RequestMethod.GET)
    public Map<String, Number> getStatisticByInstrumentId(@PathVariable(value = "instrumentId") String instrumentId){
        CompletableFuture<Map<String, Number>> statisticsFuture = transactionService.getStatisticByInstrumentId(instrumentId);
        try {
            return statisticsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.getMessage();
        }
        return null;
    }
}

