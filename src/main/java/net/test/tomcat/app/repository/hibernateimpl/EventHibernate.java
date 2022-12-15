package net.test.tomcat.app.repository.hibernateimpl;

import net.test.tomcat.app.entities.Event;
import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.EventRepository;
import net.test.tomcat.app.repository.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventHibernate implements EventRepository {


    @Override
    public Event getById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("select e from Event e  left join fetch e.files where e.id=:id", Event.class)
                    .setParameter("id", id).uniqueResult();

        }
    }

    @Override
    public List<Event> getAll() {
        try(Session session = HibernateUtil.getSession()){
            return session.createQuery("Select distinct e from Event e left join fetch e.files").list();
        }
    }

    @Override
    public Event save(Event event) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(event);
        session.getTransaction().commit();
        session.close();
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(event);
        session.getTransaction().commit();
        session.close();
        return event;
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Event event = session.get(Event.class, id);
        session.delete(event);
        session.getTransaction().commit();
        session.close();
    }
}
