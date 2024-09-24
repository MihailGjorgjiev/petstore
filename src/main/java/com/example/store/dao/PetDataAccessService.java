package com.example.store.dao;

import com.example.store.model.Pet;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("petRepo")
public class PetDataAccessService implements PetRepository{
    private static List<Pet> DB=new ArrayList<>();
    @Override
    public int insertPet(UUID id, Pet pet) {
        if(DB.size()==20) return 0;
        DB.add(new Pet(id,pet.getName(),pet.getType(),pet.getDescription(),pet.getDateOfBirth(),pet.getRating()));
        return 1;
    }

    @Override
    public List<Pet> selectAllPets() {
        return DB;
    }

    @Override
    public Optional<Pet> selectPetById(UUID id) {
        return DB.stream()
                .filter(pet -> pet.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePetById(UUID id) {
        Optional<Pet> petMaybe = selectPetById(id);
        if (petMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(petMaybe.get());
        return 1;
    }

    @Override
    public int updatePetById(UUID id, Pet update) {
        return selectPetById(id)
                .map(pet -> {
                    int indexOfPetToUpdate=DB.indexOf(pet);
                    if(indexOfPetToUpdate>=0){
                        DB.set(indexOfPetToUpdate,new Pet(id,update.getName(), update.getType(),update.getDescription(),update.getDateOfBirth(),update.getRating()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }




}
