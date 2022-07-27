package org.generation.jfs_data_jpa_demo.services;

import org.generation.jfs_data_jpa_demo.entities.User;
import org.generation.jfs_data_jpa_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findOne(Long id) {
        return userRepo.findById(id);
    }

    public User save(User newUser) {
        return userRepo.save(newUser);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public long count() {
        return userRepo.count();
    }
}

