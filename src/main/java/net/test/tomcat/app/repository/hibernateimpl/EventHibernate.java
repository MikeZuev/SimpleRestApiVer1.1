package net.test.tomcat.app.repository.hibernateimpl;

import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.EventRepository;
import net.test.tomcat.app.repository.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventHibernate implements EventRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Event getById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select e from Event e where e.id=:id", Event.class)
                    .setParameter("id", id).uniqueResult();

        }
    }

    @Override
    public List<Event> getAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("Select distinct e from Event e ").list();
        }
    }

    @Override
    public Event save(Event event) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(event);
        session.getTransaction().commit();
        session.close();
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(event);
        session.getTransaction().commit();
        session.close();
        return event;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Event event = session.get(Event.class, id);
        session.delete(event);
        session.getTransaction().commit();
        session.close();
    }
}
