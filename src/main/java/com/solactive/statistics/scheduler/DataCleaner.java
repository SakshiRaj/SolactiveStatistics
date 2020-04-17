package com.solactive.statistics.scheduler;

import com.solactive.statistics.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class DataCleaner {

    private static final long ONE_MINUTE_IN_MILLIS = 60 * 1000L;

    @Autowired
    private TransactionService transactionService;

    @Scheduled(fixedRate = ONE_MINUTE_IN_MILLIS)
    public void cleanOldData() {
        transactionService.deleteOldTransactions();
    }
}