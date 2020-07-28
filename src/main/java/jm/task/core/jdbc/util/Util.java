package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/coretaskdb?USER=root&password=" +
                        "Root1067!&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
    }

    private static Configuration getConfiguration() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/coretaskdb" +
                "?serverTimezone=Europe/Moscow");
        configuration.setProperty("dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "Root1067!");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        //configuration.setProperty("hibernate.current_session_context_class", "thread");
        //configuration.addPackage("jm.task.core.jdbc.model.User");
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}