package org.example.DZ2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DZ2.DAO.UserDAO;
import org.example.DZ2.DAO.UserDAOImpl;
import org.example.DZ2.Entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StartService {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final Logger logger = LogManager.getLogger(StartService.class);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            switch (sc.nextLine()) {
                case "1" -> createUser();
                case "2" -> updateUser();
                case "3" -> deleteUser();
                case "4" -> userFindById();
                case "5" -> userFindByAll();
                case "6" -> running = false;
            }
        }
    }

    public static void printMenu() {
        //Меню выбора
        System.out.println("Что нужно сделать с пользователем:\n" +
                "1. Добавить нового пользователя в БД\n" +
                "2. Обновить данные пользователя в БД\n" +
                "3. Удалить пользователя из БД\n" +
                "4. Найти пользователя по id\n" +
                "5. Показать всех пользователей в БД\n" +
                "6. Выход из сервиса"
        );
    }

    private static void createUser() {
        //Создание пользователя
        System.out.println("\nДобавление нового пользователя");
        System.out.print("Введите имя: ");
        String name = sc.nextLine();

        System.out.print("Введите Email: ");
        String email = sc.nextLine();

        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(sc.nextLine());

        User user = new User(name, email, age, LocalDate.now());
        User savedUser = userDAO.save(user);
        System.out.println("Пользователь создан успешно: " + savedUser);
        logger.info("Создан новый пользователь с id: {}", savedUser.getId());
    }

    private static void updateUser() {
        System.out.print("\nEnter user ID to update: ");
        Long id = Long.parseLong(sc.nextLine());

        var userOptional = userDAO.findById(id);
        if (userOptional.isEmpty()) {
            System.out.println("С указанным id: " + id + " пользователь на найден");
            return;
        }

        User user = userOptional.get();
        System.out.println("Данные текущего пользователя: " + user);

        System.out.print("Введите новое имя, если оставить поле пустым сохранится прошлое имя): ");
        String name = sc.nextLine();
        if (!name.isBlank()) {
            user.setName(name);
        }

        System.out.print("Введите новый email, если оставить поле пустым сохранится прошлый email: ");
        String email = sc.nextLine();
        if (!email.isBlank()) {
            user.setEmail(email);
        }

        System.out.print("Введите новый возраст, если поле оставить пустым сохранится прошлый возраст: ");
        String ageInput = sc.nextLine();
        if (!ageInput.isBlank()) {
            user.setAge(Integer.parseInt(ageInput));
        }

        User updatedUser = userDAO.update(user);
        System.out.println("Обновление данных пользователей прошло успешно: " + updatedUser);
        logger.info("Обновление данных пользователя с ID: {}", updatedUser.getId());
    }

    private static void deleteUser() {
        System.out.print("\nВведите id пользователя для удаления: ");
        Long id = Long.parseLong(sc.nextLine());

        var userOptional = userDAO.findById(id);
        if (userOptional.isEmpty()) {
            System.out.println("С указанным id: " + id + " пользователь на найден");
            return;
        }

        System.out.println("Вы уверены что хотите удалить данного пользователя? (Да/Нет)");
        System.out.println(userOptional.get());
        String confirmation = sc.nextLine();

        if ("Да".equalsIgnoreCase(confirmation)) {
            userDAO.delete(id);
            System.out.println("Удаление пользователя прошло успешно.");
            logger.info("Удаление пользователя с ID: {}", id);
        } else {
            System.out.println("Удаление пользователя отменено.");
        }
    }

    private static void userFindById() {
        System.out.print("\nВведите Id пользователя: ");
        Long id = Long.parseLong(sc.nextLine());

        var user = userDAO.findById(id);
        if (user.isPresent()) {
            System.out.println("Пользователь найден: " + user.get());
        } else {
            System.out.println("С указанным id: " + id + " пользователь на найден");
        }
    }

    private static void userFindByAll() {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            System.out.println("\nСписок пользователей:");
            users.forEach(System.out::println);
        }
    }
}
