package com.example.bankcards.dto.user;

import com.example.bankcards.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity (UserRequest userRequest) {
        User user = User.builder()
                .enabled(true)
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(userRequest.role())
                .build();

        return user;
    }

    public UserResponse toResponse (User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.isEnabled()
        );

    }
}
