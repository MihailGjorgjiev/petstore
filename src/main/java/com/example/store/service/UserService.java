package com.example.store.service;

import com.example.store.dao.UserRepository;
//import com.example.store.model.Person;
import com.example.store.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepo") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int addUser(User user){
        return userRepository.insertUser(user);
    }

    public int createUsers(int numUsers) throws IOException, InterruptedException {
        if(numUsers<0) numUsers=0;
        if(numUsers>10) numUsers=10;

        for (int i=0;i<numUsers;i++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://randomuser.me/api/"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = null;
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            String firstName = json.split("first")[1].substring(3).split("\"")[0];
            String lastName = json.split("last")[1].substring(3).split("\"")[0];
            String email = json.split("email")[1].substring(3).split("\"")[0];
            float budget = (float) (Math.random() * 50);
            //30.12345 mnozi so 100  -> 3012.345 -> convert to int osttranuva decimali -> convert to float 3012.0
            // i deli so 100 30.12
            budget = (float) ((int) (budget * 100)) / 100;


            User user = new User(UUID.randomUUID(), firstName, lastName, email, budget);
            userRepository.insertUser(user);
        }
        return 1;
    }
    public List<User> getAllPeople(){
        return userRepository.selectAllUsers();
    }

    public Optional<User> getUserById(UUID id){
        return userRepository.selectUserById(id);
    }

    public int deleteUser(UUID id){
        return userRepository.deleteUserById(id);
    }

    public  int updateUser(UUID id,User user){
        return userRepository.updateUserById(id,user);
    }
}
