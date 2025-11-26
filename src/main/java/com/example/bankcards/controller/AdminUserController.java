package com.example.bankcards.controller;

import com.example.bankcards.dto.user.UserRequest;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

//    private final CardService cardService;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(AdminUserController.class);

    public AdminUserController(CardService cardService, UserService userService) {
        this.userService = userService;
//        this.cardService = cardService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Called getAllUsers");
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }


    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        log.info("Called create user: username = {}", userRequest.username());
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.createUser(userRequest));
        } catch (Exception e ) {
            return ResponseEntity.status(405).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        log.info("Called update user: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.updateUser(id, userRequest));
        } catch (Exception e ) {
            return ResponseEntity.status(405).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        log.info("Called delete user: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

}
