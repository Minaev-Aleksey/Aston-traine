package org.example.DZ2.DAO;

import org.example.DZ2.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    /**
     * Метод возвращает id пользователя
     * @param id
     * @return
     */
    Optional<User> findById(Long id);

    /**
     * Метод собирает всех пользователей таблицы dz_user, собирает в List и выводит в консоль
     * @return
     */
    List<User> findAll();

    /**
     * Метод добавляет пользователя в таблицу dz_user
     * @param user
     * @return
     */
    User save(User user);

    /**
     * Метод обновляет данные пользователя по указанному id
     * @param user
     * @return
     */
    User update(User user);

    /**
     * Метод удаляет пользователя из таблицы по указанному id
     * @param id
     */
    void delete(Long id);
}