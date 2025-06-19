package org.example.DZ2.DAO;

import org.example.DZ2.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    /**
     * Метод возвращает id пользователя
     * @param id - числовой идентификатор пользователя
     * @return - возвращает найденного пользователя
     */
    Optional<User> findById(Long id);

    /**
     * Метод собирает всех пользователей таблицы dz_user, собирает в List и выводит в консоль
     * @return - возвращает список пользователей
     */
    List<User> findAll();

    /**
     * Метод добавляет пользователя в таблицу dz_user
     * @param user - конструктор с параметрами для добавления пользователя
     * @return- возвращает созданного пользователя
     */
    User save(User user);

    /**
     * Метод обновляет данные пользователя по указанному id
     * @param user  - конструктор с параметрами для обновления данных о пользователе
     * @return- возвращает обновленного пользователя
     */
    User update(User user);

    /**
     * Метод удаляет пользователя из таблицы по указанному id
     * @param id - числовой идентификатор пользователя
     */
    void delete(Long id);
}