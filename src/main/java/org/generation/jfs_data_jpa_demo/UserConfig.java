package org.generation.jfs_data_jpa_demo;

import org.generation.jfs_data_jpa_demo.entities.User;
import org.generation.jfs_data_jpa_demo.repositories.UserRepository;
import org.generation.jfs_data_jpa_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class UserConfig {
    @Autowired
    private UserService userService;

    @Bean
    public CommandLineRunner demo(UserRepository userRepo) {
        return (args) -> {
            userRepo.save(new User("Bill"));
            userRepo.save(new User("Adam"));
            userRepo.save(new User("Jacin"));
            userRepo.save(new User("Emily"));
            System.out.println(userRepo.count());
        };
    }
}
