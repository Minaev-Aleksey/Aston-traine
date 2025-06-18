package org.example.DZ2.DAO;

import org.example.DZ2.ConnectionDB.ConnectionDB;
import org.example.DZ2.Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    public static Transaction transaction = null;
    public static final ConnectionDB connectionDB = new ConnectionDB();

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = connectionDB.connectionDB().openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            transaction.commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = connectionDB.connectionDB().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public User save(User user) {
        try (Session session = connectionDB.connectionDB().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При сохранении пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }

    @Override
    public User update(User user) {
        try (Session session = connectionDB.connectionDB().openSession()) {
            transaction = session.beginTransaction();
            User updatedUser = session.merge(user);
            transaction.commit();
            return updatedUser;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При обновления пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = connectionDB.connectionDB().openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("При удалении пользователя произошла ошибка: " + e.getMessage(), e);
        }
    }
}
