package com.example.bankcards.service;

import com.example.bankcards.dto.user.UserMapper;
import com.example.bankcards.dto.user.UserRequest;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers () {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }


    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }


    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRequest.role() != null) {
            user.setRole(userRequest.role());
        }

        if (userRequest.username() != null) {
            user.setUsername(userRequest.username());
        }
        if (userRequest.password() != null) {
            user.setPassword(userRequest.password());
        }

        userRepository.save(user);
        return (userMapper.toResponse(user));
    }

    public UserResponse deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setEnabled(false);
        userRepository.delete(user);
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return userMapper.toResponse(user);
    }

}
