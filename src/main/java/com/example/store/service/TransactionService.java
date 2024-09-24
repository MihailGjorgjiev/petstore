package com.example.store.service;

import com.example.store.dao.PetRepository;
import com.example.store.dao.TransactionRepository;
import com.example.store.dao.UserRepository;
import com.example.store.enums.Type;
import com.example.store.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, PetRepository petRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }
    public int addTransaction(Transaction transaction){
        return transactionRepository.insertTransaction(transaction);
    }
    public void purchasePets(){
        int purchaseCount=0;
        int notPuchasedCount=0;
        for (int i=0; i<userRepository.selectAllUsers().size();i++){
            for (int j=0;j<petRepository.selectAllPets().size();j++){
                if (petRepository.selectAllPets().get(j).getOwner() != null) continue;

                if (petRepository.selectAllPets().get(j).getPrice() > userRepository.selectAllUsers().get(i).getBudget()){
                    notPuchasedCount++;
                    continue;
                }

                petRepository.selectAllPets().get(j).setOwner(userRepository.selectAllUsers().get(i));
                userRepository.selectAllUsers().get(i).setBudget(userRepository.selectAllUsers().get(i).getBudget() - petRepository.selectAllPets().get(j).getPrice());
                purchaseCount++;
                //TODO:Change setter with update method
//                this.transactionRepository.updateTransactionById(petRepository.selectAllPets().get(j));
//                this.userService.edit(userRepository.findAll().get(i));

                if (petRepository.selectAllPets().get(j).getType()== Type.CAT){
                    System.out.printf("Meow, cat %s has owner %s",this.petRepository.selectAllPets().get(i).getName(), this.petRepository.selectAllPets().get(i).getOwner());
                } else {
                    System.out.printf("Woof, dog %s has owner %s",this.petRepository.selectAllPets().get(i).getName(), this.petRepository.selectAllPets().get(i).getOwner());

                }
            }
        }
        Transaction transaction=new Transaction(UUID.randomUUID(),new Date(),purchaseCount,notPuchasedCount);
        this.addTransaction(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.selectAllTransactions();
    }

    public Optional<Transaction> getTransactionById(UUID id){
        return transactionRepository.selectTransactionById(id);
    }

    public int deleteTransaction(UUID id){
        return transactionRepository.deleteTransactionById(id);
    }

    public int updateTransaction(UUID id,Transaction transaction){
        return  transactionRepository.updateTransactionById(id,transaction);
    }
}
