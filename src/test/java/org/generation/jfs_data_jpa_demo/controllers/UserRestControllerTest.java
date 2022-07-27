package org.generation.jfs_data_jpa_demo.controllers;

import org.apache.catalina.connector.Response;
import org.generation.jfs_data_jpa_demo.entities.User;
import org.generation.jfs_data_jpa_demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import javax.management.OperationsException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @LocalServerPort
    private int port;

    public static final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    public UserRestControllerTest(UserRepository userRepo) {
        if (userRepo.count() == 0) {                // load first four users if DB is empty
            userRepo.save(new User("Bill"));
            userRepo.save(new User("Adam"));
            userRepo.save(new User("Jacin"));
            userRepo.save(new User("Emily"));
            System.out.println(userRepo.count());
        }
    }

    @Test
    void testGetUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + port + "/users", User[].class);
        assertNotNull(response);
        assertEquals(Response.SC_OK, response.getStatusCodeValue());
        System.out.println(Arrays.toString(response.getBody()));
    }

    @Test
    void testFindOneUser() {
        fetchUser(2L);
    }

    @Test
    void testUserCount() {
        getCount();
    }

    @Test
    void testNewUser() {
        preOp("Post");
        postNewUser("Teka");
        postOp("Post");
    }

    @Test
    void testUpdateUser() {
        preOp("Put");
        User user = postNewUser("Lou");     // Create a new user Lou
        Long userId = user.getId();

        restTemplate.put(baseUrl + port + "/users/" + userId, new User("Louis"));   // Update to Louis

        fetchUser(userId);
        postOp("Put");
    }

    @Test
    void testDelete() {
        int before = getCount();
        preOp("Delete");
        restTemplate.delete(baseUrl + port + "/users/delete/1");
        assertDoesNotThrow(() -> OperationsException.class);
        int after = getCount();
        postOp("Delete");
        assertEquals(before - 1, after);                    // Verify delete worked
    }

    private void fetchUser(Long userId) {
        ResponseEntity<User> userResponse = restTemplate.getForEntity(baseUrl + port + "/users/" + userId, User.class);
        assertNotNull(userResponse);
        assertEquals(Response.SC_OK, userResponse.getStatusCodeValue());
        System.out.println(userResponse.getBody());
    }

    private int getCount() {
        String url = baseUrl + port + "/users/count";
        return restTemplate.getForObject(url, Integer.class);
    }

    private User postNewUser(String name) {
        User user = new User(name);
        User newUser = restTemplate.postForObject(baseUrl + port + "/users/", user, User.class);
        assertNotNull(newUser);
        System.out.println(newUser);
        return newUser;
    }

    private void preOp(String put) {
        System.out.printf("%s\nBefore %s: %d students\n", "*".repeat(25), put, getCount());
    }

    private void postOp(String Post) {
        System.out.printf("After %s: %d students\n%s\n", Post, getCount(), "*".repeat(25));
    }
}
