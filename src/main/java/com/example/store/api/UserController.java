package com.example.store.api;

import com.example.store.model.User;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public void addUser(@Valid @NonNull @RequestBody User user){
        userService.addUser(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return  userService.getAllPeople();
    }
    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id){
        return userService.getUserById(id)
                .orElse(null);
    }
    @DeleteMapping(path = "{id}")
    public void deleteUserById(@PathVariable("id") UUID id){
        userService.deleteUser(id);
    }
    @PutMapping(path = "{id}")
    public  void updateUser(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody User user){
        userService.updateUser(id,user);
    }
    @PostMapping(path = "/create-all/{numUsers}")
    public void createPets(@PathVariable("numUsers") int numUsers,@RequestBody Object obj) throws IOException, InterruptedException {

        userService.createUsers(numUsers);
    }
}
