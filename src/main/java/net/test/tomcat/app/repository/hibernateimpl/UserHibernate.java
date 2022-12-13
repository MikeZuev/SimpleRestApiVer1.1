package net.test.tomcat.app.repository.hibernateimpl;

import net.test.tomcat.app.entities.User;
import net.test.tomcat.app.repository.HibernateUtil;
import net.test.tomcat.app.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserHibernate implements UserRepository {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User getById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.createQuery("select u from User u where u.id =: id", User.class)
                .setParameter("id", id).uniqueResult();
        session.getTransaction().commit();
        session.close();

        return user;

        }





    @Override
    public List<User> getAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("Select distinct u from User u ").list();
        }

    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public User update(User user) {
        Session session =sessionFactory.openSession();
        session.beginTransaction();

        session.update(user);
        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();


    }
}
