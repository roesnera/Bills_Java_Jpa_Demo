package org.generation.jfs_data_jpa_demo.services;

import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.generation.jfs_data_jpa_demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    UserService userService;

    @Test
    void testFindAll() {
        User newUser = new User("Bill");
        userService.save(newUser);
        List<User> users = userService.getUsers();
        users.forEach(System.out::println);
    }

    @Test
    void testFindOne() {
        User newUser = new User("Bill");
        userService.save(newUser);
        Optional<User> user = userService.findOne(1L);
        user.ifPresent(System.out::println);
    }

    @Test
    void testSave() {
        User newUser = new User("Bill");
        User user = userService.save(newUser);
        System.out.println(user);
    }

    @Test
    void testDelete() {
        User newUser = new User("Bill");
        User user = userService.save(newUser);
        assertEquals(1L, userService.count());
        userService.delete(user);
        assertEquals(0L, userService.count());

    }
}
