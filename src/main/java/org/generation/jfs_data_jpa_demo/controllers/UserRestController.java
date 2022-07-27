package org.generation.jfs_data_jpa_demo.controllers;

import org.generation.jfs_data_jpa_demo.entities.User;
import org.generation.jfs_data_jpa_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;



    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        users.forEach(System.out::println);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        Optional<User> userOptional = userService.findOne(id);
        return userOptional.map(user
                        -> new ResponseEntity<>(user, HttpStatus.OK))        // student found
                .orElseGet(()
                        -> new ResponseEntity<>(HttpStatus.NOT_FOUND));         // not found
    }


    @GetMapping("/users/count")                                                           // tested
    public ResponseEntity<Integer> getUserCount() {
        return new ResponseEntity<>((int) userService.count(), HttpStatus.OK);
    }

    @PostMapping("/users")              // create new user                                 tested
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")               // update existing user
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
            @RequestBody User userDetails) throws IllegalCallerException {
        User user = userService.findOne(userId).orElseThrow(() ->
                new IllegalCallerException("User not found for this id :: " + userId));
        user.setName(userDetails.getName());
        final User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/delete/{id}")    // ** error
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        Optional<User> userData = userService.findOne(id);
        User user;
        try {
            userData.ifPresent(value -> userService.delete(value));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
