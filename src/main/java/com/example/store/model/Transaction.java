package com.example.store.model;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private Date executionDate;
    private int usersThatBoughtCount;
    private int usersThatDidntBoughtCount;

    public Transaction(UUID id, Date executionDate, int usersThatBoughtCount, int usersThatDidntBoughtCount) {
        this.id = id;
        this.executionDate = executionDate;
        this.usersThatBoughtCount = usersThatBoughtCount;
        this.usersThatDidntBoughtCount = usersThatDidntBoughtCount;
    }

    public UUID getId() {
        return id;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public int getUsersThatBoughtCount() {
        return usersThatBoughtCount;
    }

    public int getUsersThatDidntBoughtCount() {
        return usersThatDidntBoughtCount;
    }
}
