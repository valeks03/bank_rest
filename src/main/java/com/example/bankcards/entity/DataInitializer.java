package com.example.bankcards.entity;

import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        userRepository.findByUsername("alex").ifPresentOrElse(
                u -> System.out.println("ℹ️  User 'alex' уже существует"),
                () -> {
                    User admin = User.builder()
                            .username("alex")
                            .password(passwordEncoder.encode("123456")) // В БД попадёт BCRYPT-хэш
                            .role(Role.ADMIN)
                            .enabled(true)
                            .build();
                    userRepository.save(admin);
                    System.out.println("✅ Создан test-пользователь: alex / 123456 (ROLE_ADMIN)");
                });
        userRepository.findByUsername("john").ifPresentOrElse(
                u -> System.out.println("User 'john' already exists"),
                () -> {
                    User user = User.builder()
                            .username("john")
                            .password(passwordEncoder.encode("123456"))
                            .role(Role.USER)
                            .enabled(true)
                            .build();
                    userRepository.save(user);
                    System.out.println("✅ Создан test-пользователь: john / 123456 (ROLE_USER)");
                }
        );
    }
}
