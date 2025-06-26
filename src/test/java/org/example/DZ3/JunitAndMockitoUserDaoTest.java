package org.example.DZ3;

import org.example.DZ2.ConnectionDB.HibernateSessionFactory;
import org.example.DZ2.DAO.UserDAOImpl;
import org.example.DZ2.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JunitAndMockitoUserDaoTest {

    @Mock
    private Query<User> query;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Spy
    private HibernateSessionFactory hibernateSessionFactory;

    private UserDAOImpl userDAO;

    @BeforeEach
    public void setUp() {
        hibernateSessionFactory = mock(HibernateSessionFactory.class);
        when(hibernateSessionFactory.getSessionFactory()).thenReturn(sessionFactory);

        userDAO = new UserDAOImpl(hibernateSessionFactory);
    }

    @Test
    public void findByIdTest() {
        User testUser = new User(1L, "Test_user", "test@email.com", 111, LocalDate.now());

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.find(User.class, 1L)).thenReturn(testUser);

        Optional<User> result = userDAO.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test_user", result.get().getName());
        verify(transaction).commit();
    }

    @Test
    public void findByAllTest() {
        User user1 = new User(1L, "Test_user1", "test@email1.com", 111, LocalDate.now());
        User user2 = new User(2L, "Test_user2", "test@email2.com", 222, LocalDate.now());

        when(hibernateSessionFactory.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery("from User", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(user1, user2));

        List<User> result = userDAO.findAll();

        assertNotNull(result, "Результат не должен быть null");
        assertEquals(2, result.size(), "Должно вернуть 2 пользователя");
        assertEquals("Test_user1", result.get(0).getName());
        assertEquals("Test_user2", result.get(1).getName());

        verify(query).getResultList();
        verify(session).close();
    }

    @Test
    public void creatUserTest() {
        when(hibernateSessionFactory.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        User testUser = new User(1L, "Test_user", "test@email.com", 111, LocalDate.now());

        User saveUser = userDAO.save(testUser);

        assertEquals(1, saveUser.getId());
        assertEquals("Test_user", saveUser.getName());

        verify(session).persist(saveUser);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateUserTest() {
        when(hibernateSessionFactory.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        User newUser = new User(1L, "Test_user", "test@email.com", 111, LocalDate.now());
        User updateUser = new User(1L, "Update_user", "update@email.com", 333, LocalDate.now());

        when(session.merge(newUser)).thenReturn(updateUser);

        User result = userDAO.update(newUser);

        assertNotNull(result);
        assertEquals(updateUser, result);
    }

    @Test
    public void deleteUserTest() {
        when(hibernateSessionFactory.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        User testUser = new User(1L, "Test_user", "test@email.com", 111, LocalDate.now());

        when(session.find(User.class, 1L)).thenReturn(testUser);

        userDAO.delete(1L);

        assertEquals(1l, testUser.getId());
        verify(transaction).commit();
        verify(session).close();
    }
}