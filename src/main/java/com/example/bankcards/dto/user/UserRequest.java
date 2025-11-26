package com.example.bankcards.dto.user;

import com.example.bankcards.entity.Role;

public record UserRequest (
        String username,
        String password,
        Role role
)
{
}
