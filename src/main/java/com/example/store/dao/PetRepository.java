package com.example.store.dao;

import com.example.store.model.Pet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository {
    int insertPet(UUID id, Pet pet);

    default int insertPet(Pet pet){
        UUID id=UUID.randomUUID();
        return insertPet(id,pet);
    }
    List<Pet> selectAllPets();

    Optional<Pet> selectPetById(UUID id);

    int deletePetById(UUID id);

    int updatePetById(UUID id,Pet pet);


}
