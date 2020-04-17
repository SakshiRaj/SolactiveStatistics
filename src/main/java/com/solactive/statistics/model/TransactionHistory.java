package com.solactive.statistics.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TRANSACTIONHISTORY")
public class TransactionHistory implements Serializable{

    @Id
    private String instrument;
    private Double amount;
    private Long txntime;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTxntime() {
        return txntime;
    }

    public void setTxntime(Long txntime) {
        this.txntime = txntime;
    }
}