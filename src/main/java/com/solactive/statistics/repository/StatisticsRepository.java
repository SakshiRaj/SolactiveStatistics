package com.solactive.statistics.repository;

import com.solactive.statistics.model.TransactionHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CrudRepository<TransactionHistory, String> {

}
