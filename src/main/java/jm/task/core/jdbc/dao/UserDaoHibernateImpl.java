package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(32) null, " +
                    " last_name VARCHAR(32) null, " +
                    " age TINYINT)").addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session = Util.getSessionFactory()
                    .openSession();
            Transaction transaction = session.beginTransaction();

            users = (List<User>) session.createSQLQuery(
                    "SELECT * FROM Users")
                    .addEntity(User.class).list();
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM Users")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }
}
