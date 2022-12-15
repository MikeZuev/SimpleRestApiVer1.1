package net.test.tomcat.app.repository;

import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    static Properties properties = new Properties();


    private HibernateUtil() {

    }


    private static synchronized SessionFactory getSessionFactory() {
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));

        } catch (Exception e) {

        }

        if (sessionFactory == null) {
            sessionFactory = new Configuration().mergeProperties(properties)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(File.class)
            .buildSessionFactory();

        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

}
