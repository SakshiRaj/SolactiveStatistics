package com.solactive.statistics.controllers;

import com.solactive.statistics.services.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {

    @InjectMocks
    StatisticsController statisticsController = new StatisticsController();

    @Mock
    TransactionService transactionService;

    @Test
    public void shouldGetTheAverageByGetStatisticsAPI(){
        Map<String, Number> statistics = new HashMap<>();
        statistics.put("average", 41.0);
        statistics.put("max", 42.0);
        statistics.put("min", 43.0);
        statistics.put("count", 44.0);
        Mockito.when(transactionService.getStatistics()).thenReturn(CompletableFuture.completedFuture(statistics));
        Assert.assertEquals(statisticsController.statistics().get("average"), 41.0);
    }
    @Test
    public void shouldGetTheAverageByGetStatisticsForInstrumentId(){
        Map<String, Number> statistics = new HashMap<>();
        statistics.put("average", 41.0);
        statistics.put("max", 42.0);
        statistics.put("min", 43.0);
        statistics.put("count", 44.0);
        Mockito.when(transactionService.getStatisticByInstrumentId(Mockito.anyString())).thenReturn(CompletableFuture.completedFuture(statistics));
        Assert.assertEquals(statisticsController.getStatisticByInstrumentId("IBM").get("average"), 41.0);
    }
}

