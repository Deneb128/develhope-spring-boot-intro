package com.example.demowebapp.controllers;

import com.example.demowebapp.entities.User;
import com.example.demowebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/user/add")
    public User addNewUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @GetMapping(path = "/user/get/all")
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @GetMapping(path = "/user/get/{id}")
    public User getUserByID(@PathVariable Long id){
        return userService.getUserByID(id);
    }

    @PutMapping(path = "/user/update/{id}/banstatus")
    public @ResponseBody User updateIsBannedByID(@PathVariable Long id, @RequestParam(required = true) Boolean isBanned){
        return userService.updateIsBannedByID(id, isBanned);
    }

    @PutMapping(path = "/user/update/{id}")
    public @ResponseBody User updateUserByID(@PathVariable Long id, @RequestBody User user){
        return userService.updateUserByID(id, user);
    }

    @DeleteMapping(path = "/user/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @DeleteMapping(path = "/user/delete/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable Long id){
        return userService.deleteUserByID(id);
    }
}
