package com.example.store.service;

import com.example.store.dao.PetRepository;
import com.example.store.enums.Type;
//import com.example.store.model.Person;
import com.example.store.model.Pet;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.util.*;

@Service
public class PetService {
    private final PetRepository petRepository;
    @Autowired
    public PetService(@Qualifier("petRepo") PetRepository petRepository) {
        this.petRepository = petRepository;
    }
    public int addPet(Pet pet){
        return petRepository.insertPet(pet);
    }
    public int createPets(int numPets) throws IOException, InterruptedException {
        List<String> catNames = new ArrayList<>(Arrays.asList("Biscuit", "Starr","Joy","Bel","Gloria","Lilo","Troy","Finnigan","Zura","Chuckie","Hopi","Kristy","Luci","Patches","Gucci","Tink","Choco","Walle","Rumi","D'Artagnan"));
        List<String> dogNames = new ArrayList<>(Arrays.asList("Seuss", "Kessel","Robbie","Greer","Jasper","Natty","Erynn","Charchoal","Siri","Gracie","Mackenzie","Lucca","Dan","Pashka","Kami","Ruth","Phebe","Hershey","Louella","Walt"));
        if(numPets<0) numPets=0;
        if(numPets>20) numPets=20;
        for(int i=0;i<numPets;i++){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.thecatapi.com/v1/breeds?limit=20&page=0"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = null;
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            List<String> tempList = List.of(json.split("name"));
            List<String> catDesc = new ArrayList<>();
            for (int j=1; j<tempList.size(); j+=2){
                catDesc.add(tempList.get(j).substring(3).split("\"")[0]);
            }
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.thedogapi.com/v1/breeds?limit=20&page=0"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            response = null;
            response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());

            String json1 = response.body();

            List<String> tempList1 = List.of(json.split("name"));
            List<String> dogDesc = new ArrayList<>();
            for (int j=1; j<tempList1.size(); j+=2){
                dogDesc.add(tempList1.get(j).substring(3).split("\"")[0]);
            }
            Date date=new Date((int)Math.floor(Math.random()*(2022-2010)+110),(int)Math.floor(Math.random()*(12-1)+1),(int)Math.floor(Math.random()*(28-1)+1));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String dateOfBirth = dateFormat.format(date.getTime());
            //Type
            Type type = Type.values()[(int) Math.round(Math.random())];

            String name = type==Type.CAT ? catNames.get(i) : dogNames.get(i);
            String description = type==Type.CAT ? catDesc.get(i) : dogDesc.get(i);


            int rating = (int) Math.floor(Math.random()*11);
            Pet pet = new Pet(UUID.randomUUID(),name, type, description, dateOfBirth, rating);
            petRepository.insertPet(pet);
        }
        return 1;
    }
    public List<Pet> getAllPets(){
        return petRepository.selectAllPets();
    }

    public Optional<Pet> getPetById(UUID id){
        return petRepository.selectPetById(id);
    }

    public int deletePet(UUID id){
        return petRepository.deletePetById(id);
    }

    public  int updatePet(UUID id,Pet pet){
        return petRepository.updatePetById(id,pet);
    }
}
