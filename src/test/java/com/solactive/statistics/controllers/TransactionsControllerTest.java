package com.solactive.statistics.controllers;

import com.solactive.statistics.model.TransactionHistory;
import com.solactive.statistics.services.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsControllerTest {

    @InjectMocks
    public TransactionsController transactionsController = new TransactionsController();

    @Mock
    private TransactionService transactionService;

    @Test
    public void shouldNotAddTransactionWithNoResponse(){
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(new Double(100));
        transactionHistory.setInstrument("143.82");
        transactionHistory.setTxntime(14781L);
        Assert.assertEquals(transactionsController.transaction(transactionHistory), new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    @Test
    public void shouldAddTransactionWithResponseCreated() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long epochInMillis = utc.toEpochSecond() * 1000;
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(100D);
        transactionHistory.setInstrument("143.82");
        transactionHistory.setTxntime((epochInMillis - (60 * 1000l)) + 1);
        Assert.assertEquals(transactionsController.transaction(transactionHistory), new ResponseEntity<>(HttpStatus.CREATED));
    }
}
