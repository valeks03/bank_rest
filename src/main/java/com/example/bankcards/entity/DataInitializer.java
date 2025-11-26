package com.example.bankcards.entity;

import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Этот метод выполнится ОДИН раз при старте приложения.
     * Тут мы создаём пользователя 'alex' с ролью ADMIN, если его ещё нет в БД.
     */
    @Override
    public void run(String... args) {
        userRepository.findByUsername("ADMIN").orElseGet(
                () -> {
                    User admin = User.builder()
                            .username("ADMIN")
                            .password(passwordEncoder.encode("ADMIN")) // В БД попадёт BCRYPT-хэш
                            .role(Role.ADMIN)
                            .enabled(true)
                            .build();
                    userRepository.save(admin);
                    return admin;
                });
    }
}
