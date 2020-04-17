package com.solactive.statistics.services;

import com.solactive.statistics.model.TransactionHistory;
import com.solactive.statistics.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class TransactionService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    private static final Long ONE_MINUTE_IN_MILLIS = 60 * 1000L;

    @Async
    public void add(TransactionHistory transactionHistory){
        statisticsRepository.save(transactionHistory);
    }

    @Async
    public CompletableFuture<Map<String, Number>> getStatistics() {
        Map<String, Number> statistics = new HashMap<>();
        List<TransactionHistory> transactionsList = (List<TransactionHistory>) statisticsRepository.findAll();
        updateStatistics(statistics, transactionsList);
        return CompletableFuture.completedFuture(statistics);
    }

    @Async
    public CompletableFuture<Map<String, Number>> getStatisticByInstrumentId(String instrumentId) {
        Map<String, Number> statistics = new HashMap<>();
        List<TransactionHistory> transactionsList = (List<TransactionHistory>) statisticsRepository.findAll();
        transactionsList = transactionsList.stream().filter(transactionHistory -> transactionHistory.getInstrument().equals(instrumentId)).collect(Collectors.toList());
        updateStatistics(statistics, transactionsList);
        return CompletableFuture.completedFuture(statistics);
    }

    private void updateStatistics(Map<String, Number> statistics, List<TransactionHistory> transactionsList) {
        OptionalDouble average = transactionsList.stream().mapToDouble(TransactionHistory::getAmount).average();
        OptionalDouble max = transactionsList.stream().mapToDouble(TransactionHistory::getAmount).max();
        OptionalDouble min = transactionsList.stream().mapToDouble(TransactionHistory::getAmount).min();
        Integer count = transactionsList.size();
        statistics.put("average", average.isPresent() ? average.getAsDouble() : 0.0);
        statistics.put("max", max.isPresent() ? max.getAsDouble() : 0.0);
        statistics.put("min", min.isPresent() ? min.getAsDouble() : 0.0);
        statistics.put("count", count);
    }

    @Async
    public void deleteOldTransactions(){
        List<TransactionHistory> transactionsList = (List<TransactionHistory>) statisticsRepository.findAll();
        removeMatching(transactionsList.iterator(), isOlderThanOneMinute());
    }

    private static <E> void removeMatching(final Iterator<E> it, final Predicate<E> predicate) {
        while (it.hasNext()) {
            final E e = it.next();
            if (predicate.test(e)) {
                it.remove();
            }
        }
    }

    private static Predicate<TransactionHistory> isOlderThanOneMinute() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long epochInMillis = utc.toEpochSecond() * 1000;
        return transactionHistory ->
                transactionHistory.getTxntime() < epochInMillis - ONE_MINUTE_IN_MILLIS;
    }
}
