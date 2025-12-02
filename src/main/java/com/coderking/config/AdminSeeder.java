package com.coderking.config;

import com.coderking.model.Role;
import com.coderking.model.User;
import com.coderking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> {
            // Check if admin already exists
            userRepository.findByEmail("rohit@coderking.com").ifPresentOrElse(
                    user -> {
                        System.out.println("✅ Admin already exists: " + user.getEmail());
                    },
                    () -> {
                        // Create admin user
                        User admin = new User();
                        admin.setName("Rohit Kumar");
                        admin.setEmail("rohit@coderking.com");
                        admin.setPassword("{noop}admin123"); // ⚠️ encode properly in production
                        admin.setRole(Role.ADMIN);
                        admin.setPoints(0);

                        userRepository.save(admin);
                        System.out.println("🚀 Admin user created: " + admin.getEmail());
                    }
            );
        };
    }
}

