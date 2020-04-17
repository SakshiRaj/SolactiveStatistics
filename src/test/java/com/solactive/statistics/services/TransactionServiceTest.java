package com.solactive.statistics.services;

import com.solactive.statistics.controllers.TransactionsController;
import com.solactive.statistics.model.TransactionHistory;
import com.solactive.statistics.repository.StatisticsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    public TransactionService transactionService = new TransactionService();

    @Mock
    private StatisticsRepository statisticsRepository;

    @Test
    public void getStatistics() throws ExecutionException, InterruptedException {
        List<TransactionHistory> transactionHistories = getTransactionHistories();
        Mockito.when(statisticsRepository.findAll()).thenReturn(transactionHistories);
        CompletableFuture<Map<String, Number>> statisticsFuture = transactionService.getStatistics();
        Assert.assertEquals(143.82, statisticsFuture.get().get("average"));
    }

    private List<TransactionHistory> getTransactionHistories() {
        List<TransactionHistory> transactionHistories = new ArrayList<>();
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setInstrument("IBM");
        transactionHistory.setAmount(143.82);
        transactionHistory.setTxntime(14781L);
        transactionHistories.add(transactionHistory);
        return transactionHistories;
    }

    @Test
    public void getStatisticByInstrumentId() throws ExecutionException, InterruptedException {
        List<TransactionHistory> transactionHistories = getTransactionHistories();
        Mockito.when(statisticsRepository.findAll()).thenReturn(transactionHistories);
        CompletableFuture<Map<String, Number>> statisticsFuture = transactionService.getStatisticByInstrumentId("IBM");
        Assert.assertEquals(143.82, statisticsFuture.get().get("average"));
    }

    @Test
    public void testAdd(){
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setInstrument("IBM");
        transactionHistory.setAmount(143.82);
        transactionHistory.setTxntime(14781L);
        transactionService.add(transactionHistory);
        Mockito.verify(statisticsRepository, Mockito.times(1)).save(transactionHistory);
    }

}
