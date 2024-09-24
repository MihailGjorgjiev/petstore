package com.example.store.api;

import com.example.store.model.Pet;
import com.example.store.service.PetService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/pets")
@RestController
public class PetController {
    private final PetService petService;
    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }
    @PostMapping
    public void addPet(@Valid @NonNull @RequestBody Pet pet){
        petService.addPet(pet);
    }
    @GetMapping
    public List<Pet> getAllPets(){
        return  petService.getAllPets();
    }
    @GetMapping(path = "{id}")
    public Pet getPetById(@PathVariable("id") UUID id){
        return petService.getPetById(id)
                .orElse(null);
    }
    @DeleteMapping(path = "{id}")
    public void deletePetById(@PathVariable("id") UUID id){
        petService.deletePet(id);
    }
    @PutMapping(path = "{id}")
    public  void updatePet(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody Pet pet){
        petService.updatePet(id,pet);
    }

    @PostMapping(path = "/create-all/{numPets}")
    public void createPets(@PathVariable("numPets") int numPets,@RequestBody Object obj) throws IOException, InterruptedException {

        petService.createPets(numPets);
    }
}
