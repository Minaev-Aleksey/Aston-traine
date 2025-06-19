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
    private static final Scanner SC = new Scanner(System.in);
    private static final UserDAO USER_DAO = new UserDAOImpl();
    private static final Logger LOGGER = LogManager.getLogger(StartService.class);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            switch (SC.nextLine()) {
                case "1" -> createUser();
                case "2" -> updateUser();
                case "3" -> deleteUser();
                case "4" -> userFindById();
                case "5" -> userFindByAll();
                case "6" -> running = false;
                default -> System.out.println("Ввели неверное число");
            }
        }
    }

    private static void printMenu() {
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
        System.out.println("\nДобавление нового пользователя");
        System.out.print("Введите имя: ");

        //Делаем проверку на ввод строки,а не числа
        String name;
        while (true) {
            name = SC.nextLine();
            if (name.matches("\\d+")) {
                System.out.println("Ошибка: введено число!\nПовторите вводе имени:");
            } else {
                break;
            }
        }

        //Делаем проверку на ввод email c нахождением @ и .
        System.out.print("Введите email: ");
        String email;
        while (true) {
            email = SC.nextLine().trim();
            if (email.contains("@") && email.indexOf("@") < email.lastIndexOf(".") && email.length() > 5) {
                break;
            } else {
                System.out.println("Некорректный email! Должен быть символ @ и домен (например, user@example.com)");
                System.out.print("Введите email: ");
            }
        }

        //Делаем проверку на ввод числа,а не строки
        System.out.print("Введите возраст: ");
        while (!SC.hasNextInt()) {
            System.out.println("Ошибка: введено не число.\nВведите возраст снова!");
            SC.next();
        }
        int age = SC.nextInt();

        User user = new User(name, email, age, LocalDate.now());
        User savedUser = USER_DAO.save(user);
        System.out.println("Пользователь создан успешно: " + savedUser);
        LOGGER.info("Создан новый пользователь с id: {}", savedUser.getId());
    }

    private static void updateUser() {
        System.out.print("\nEnter user ID to update: ");
        Long id = SC.nextLong();

        var userOptional = USER_DAO.findById(id);
        if (userOptional.isEmpty()) {
            System.out.println("С указанным id: " + id + " пользователь на найден");
            return;
        }

        User user = userOptional.get();
        System.out.println("Данные текущего пользователя: " + user);

        System.out.print("Введите новое имя, если оставить поле пустым сохранится прошлое имя): ");
        String name = SC.nextLine();
        if (!name.isBlank()) {
            user.setName(name);
        }

        System.out.print("Введите новый email, если оставить поле пустым сохранится прошлый email: ");
        String email = SC.nextLine();
        if (!email.isBlank()) {
            user.setEmail(email);
        }

        System.out.print("Введите новый возраст, если поле оставить пустым сохранится прошлый возраст: ");
        int ageInput = SC.nextInt();
        if (ageInput != 0) {
            user.setAge(ageInput);
        }

        User updatedUser = USER_DAO.update(user);
        System.out.println("Обновление данных пользователей прошло успешно: " + updatedUser);
        LOGGER.info("Обновление данных пользователя с ID: {}", updatedUser.getId());
    }

    private static void deleteUser() {
        System.out.print("\nВведите id пользователя для удаления: ");
        Long id = SC.nextLong();

        var userOptional = USER_DAO.findById(id);
        if (userOptional.isEmpty()) {
            System.out.println("С указанным id: " + id + " пользователь на найден");
            return;
        }

        System.out.println("Вы уверены что хотите удалить данного пользователя? (Да/Нет)");
        System.out.println(userOptional.get());
        String confirmation = SC.nextLine();

        if ("Да".equalsIgnoreCase(confirmation)) {
            USER_DAO.delete(id);
            System.out.println("Удаление пользователя прошло успешно.");
            LOGGER.info("Удаление пользователя с ID: {}", id);
        } else {
            System.out.println("Удаление пользователя отменено.");
        }
    }

    private static void userFindById() {
        System.out.print("\nВведите Id пользователя: ");
        Long id = SC.nextLong();

        var user = USER_DAO.findById(id);
        if (user.isPresent()) {
            System.out.println("Пользователь найден: " + user.get());
        } else {
            System.out.println("С указанным id: " + id + " пользователь на найден");
        }
    }

    private static void userFindByAll() {
        List<User> users = USER_DAO.findAll();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            System.out.println("\nСписок пользователей:");
            users.forEach(System.out::println);
        }
    }
}