package org.example.DZ2.DAO;

import org.example.DZ2.ConnectionDB.DbConnectionUtil;
import org.example.DZ2.Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static Transaction TRANSACTION = null;
    private static final DbConnectionUtil DB_CONNECTION_UTIL = new DbConnectionUtil();

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = DB_CONNECTION_UTIL.getSessionFactory().openSession()) {
            TRANSACTION = session.beginTransaction();
            User user = session.find(User.class, id);
            TRANSACTION.commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            if (TRANSACTION != null) {
                TRANSACTION.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = DB_CONNECTION_UTIL.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public User save(User user) {
        try (Session session = DB_CONNECTION_UTIL.getSessionFactory().openSession()) {
            TRANSACTION = session.beginTransaction();
            session.persist(user);
            TRANSACTION.commit();
            return user;
        } catch (Exception e) {
            if (TRANSACTION != null) {
                TRANSACTION.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При сохранении пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }

    @Override
    public User update(User user) {
        try (Session session = DB_CONNECTION_UTIL.getSessionFactory().openSession()) {
            TRANSACTION = session.beginTransaction();
            User updatedUser = session.merge(user);
            TRANSACTION.commit();
            return updatedUser;
        } catch (Exception e) {
            if (TRANSACTION != null) {
                TRANSACTION.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При обновления пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = DB_CONNECTION_UTIL.getSessionFactory().openSession()) {
            TRANSACTION = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            TRANSACTION.commit();
        } catch (Exception e) {
            if (TRANSACTION != null) {
                TRANSACTION.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При удалении пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }
}
