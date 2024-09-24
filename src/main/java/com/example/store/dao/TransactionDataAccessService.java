package com.example.store.dao;

import com.example.store.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("transactionRepo")
public class TransactionDataAccessService implements TransactionRepository{
    private static List<Transaction> DB=new ArrayList<>();
    @Override
    public int insertTransaction(UUID id, Transaction transaction) {
        DB.add(new Transaction(id,transaction.getExecutionDate(),transaction.getUsersThatBoughtCount(),transaction.getUsersThatDidntBoughtCount()));
        return 1;
    }

    @Override
    public List<Transaction> selectAllTransactions() {
        return DB;
    }

    @Override
    public Optional<Transaction> selectTransactionById(UUID id) {
        return DB.stream()
                .filter(t->t.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteTransactionById(UUID id) {
        Optional<Transaction> transactionMaybe=selectTransactionById(id);
        if(transactionMaybe.isEmpty()) return 0;
        DB.remove(transactionMaybe.get());
        return 1;
    }

    @Override
    public int updateTransactionById(UUID id, Transaction update) {
        return selectTransactionById(id)
                .map(transaction -> {
                    int indexofTransactionToUpdate=DB.indexOf(transaction);
                    if(indexofTransactionToUpdate>=0){
                        DB.set(indexofTransactionToUpdate,new Transaction(id,update.getExecutionDate(),update.getUsersThatBoughtCount(),update.getUsersThatDidntBoughtCount()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
