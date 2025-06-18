package org.example.DZ2.DAO;

import org.example.DZ2.Entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {

    private static final UserDAO userDAO = new UserDAOImpl();
    User newUser = new User("Test User", "test@example.com", 30, LocalDate.now());

    @Test
    void findById() {
        userDAO.save(newUser);
        Optional<User> newId = userDAO.findById(newUser.getId());

        assertTrue(newId.isPresent());
    }

    @Test
    void findAll() {
        List<User> listUser = userDAO.findAll();
        assertFalse(listUser.isEmpty());
    }

    @Test
    void save() {
        User savedUser = userDAO.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(30, savedUser.getAge());
        assertNotNull(savedUser.getLocalDate());
    }

    @Test
    void update() {
        userDAO.save(newUser);
        newUser.setName("New testUser");
        newUser.setEmail("new_test@example.com");
        newUser.setAge(45);

        User updateUser = userDAO.update(newUser);
        assertEquals(newUser.getId(), updateUser.getId());
        assertEquals("New testUser", updateUser.getName());
        assertEquals("new_test@example.com",updateUser.getEmail());
        assertEquals(45,updateUser.getAge());
    }

    @Test
    void delete() {
        userDAO.save(newUser);
        userDAO.delete(newUser.getId());

        Optional<User> deleteUser = userDAO.findById(newUser.getId());
        assertTrue(deleteUser.isEmpty());
    }
}