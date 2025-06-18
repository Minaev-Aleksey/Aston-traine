package org.example.DZ2.DAO;

import org.example.DZ2.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}