package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<User> query = session.createNativeQuery("CREATE TABLE IF NOT EXISTS userstable (Id INT AUTO_INCREMENT PRIMARY KEY, Name VARCHAR(20), LastName VARCHAR(20), Age INT)", User.class);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При создании таблицы произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<User> query = session.createNativeQuery("DROP TABLE IF EXISTS userstable", User.class);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При удалении таблицы произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При добавлении User'a произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User userToDelete = session.load(User.class, id);
                if (userToDelete != null) {
                    session.delete(userToDelete);
                }
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При удалении пользователя произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> res = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                res = session.createQuery("SELECT u FROM User u", User.class).list();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При получении списка всех пользователей произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При очистке таблицы произошла ошибка");
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
