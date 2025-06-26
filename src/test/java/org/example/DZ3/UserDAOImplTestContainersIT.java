package org.example.DZ3;

import org.example.DZ2.ConnectionDB.HibernateSessionFactory;
import org.example.DZ2.DAO.UserDAO;
import org.example.DZ2.DAO.UserDAOImpl;
import org.example.DZ2.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Данные тесты сработают при нахождении кода и docker с БД в одной среде то.
 * P.S. с проблемой справился тесты отрабатывают корректно.
 */

@Testcontainers
public class UserDAOImplTestContainersIT {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private static SessionFactory sessionFactory;
    private static UserDAO userDAO;

    @BeforeAll
    static void setup() {

        postgres.start();

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                .addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
        userDAO = new UserDAOImpl(new HibernateSessionFactory(sessionFactory));
    }

    @BeforeEach
    void init() {
        userDAO = new UserDAOImpl(new HibernateSessionFactory(sessionFactory));

        recreateTestData();
    }

    private void recreateTestData() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.createMutationQuery("DELETE FROM User").executeUpdate();
        }
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }

        postgres.stop();
    }

    @Test
    @Order(1)
    void findAllNoUsersTest() {
        List<User> result = userDAO.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(2)
    void findAllTest() {

        User user1 = new User(null, "Test_name1", "test1@email.com", 111, LocalDate.now());
        User user2 = new User(null, "Test_name2", "test2@email.com", 222, LocalDate.now());

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user1);
            session.persist(user2);
            tx.commit();
        }

        List<User> result = userDAO.findAll();

        assertEquals(2, result.size());
        assertEquals("Test_name1", result.get(0).getName());
        assertEquals("Test_name2", result.get(1).getName());
    }

    @Test
    @Order(3)
    void saveUserTest() {
        User user = new User(null, "Test_name", "test@email.com", 111, LocalDate.now());

        userDAO.save(user);

        assertNotNull(user.getId());

        try (Session session = sessionFactory.openSession()) {
            User saveUser = session.find(User.class, user.getId());
            assertEquals(user.getName(), saveUser.getName());
        }
    }

    @Test
    @Order(4)
    void findByIdUserTest() {
        User user = new User(null, "Test_name", "test@email.com", 111, LocalDate.now());

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }

        Optional<User> result = userDAO.findById(user.getId());

        assertTrue(result.isPresent());
        assertEquals("Test_name", result.get().getName());

    }

    @Test
    @Order(5)
    void updateUserTest() {
        User user = new User(null, "Test_name0", "test0@email.com", 111, LocalDate.now());

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }

        user.setName("Update_name");
        user.setEmail("update@email.com");
        User result = userDAO.update(user);

        assertNotEquals(user, result);

        try (Session session = sessionFactory.openSession()) {
            User updatedUser = session.find(User.class, user.getId());
            assertEquals("Update_name", updatedUser.getName());
            assertEquals("update@email.com", updatedUser.getEmail());
        }
    }

    @Test
    @Order(6)
    void deleteUserTest() {
        User user = new User(null, "Test_name0", "test0@email.com", 111, LocalDate.now());

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }

        userDAO.delete(user.getId());

        assertNotNull(user.getId());

        try (Session session = sessionFactory.openSession()) {
            User deletedUser = session.find(User.class, user.getId());
            assertNull(deletedUser);
        }
    }
}