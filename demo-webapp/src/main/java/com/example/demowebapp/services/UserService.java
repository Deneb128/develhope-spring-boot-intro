package com.example.demowebapp.services;

import com.example.demowebapp.entities.User;
import com.example.demowebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addNewUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List getUserList() {
        return userRepository.findAll();
    }

    public User getUserByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public User updateUserByID(Long id, User user) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            user.setId(optUser.get().getId());
            return userRepository.saveAndFlush(user);
        }
        return null;
    }

    public User updateIsBannedByID(Long id, Boolean isBanned) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            user.setIsBanned(isBanned);
            return userRepository.saveAndFlush(user);
        } else {
            return null;
        }
    }

    public ResponseEntity<?> deleteUserByID(Long id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(User user) {
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findById(user.getId()).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        try {
            userRepository.delete(user);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
