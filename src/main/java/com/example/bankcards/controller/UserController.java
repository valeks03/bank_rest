package com.example.bankcards.controller;


import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/new-user")
public class UserController  {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/user")
    public ResponseEntity<String> createNewUser(@RequestParam String username, @RequestParam String password ) {
        User user = User.builder()
                .username(username)
                .password(password)
                .enabled(true)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User %s was created".formatted(username));
    }


    @PostMapping("/admin")
    public ResponseEntity<String> createNewAdmin(@RequestParam String username, @RequestParam String password ) {
        User user = User.builder()
                .username(username)
                .password(password)
                .enabled(true)
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin %s was created".formatted(username));
    }
}
