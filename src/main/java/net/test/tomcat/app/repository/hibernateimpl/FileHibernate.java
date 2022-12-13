package net.test.tomcat.app.repository.hibernateimpl;

import net.test.tomcat.app.entities.File;
import net.test.tomcat.app.repository.FileRepository;
import net.test.tomcat.app.repository.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class FileHibernate implements FileRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public File getById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select f from File f where f.id=:id", File.class)
                    .setParameter("id", id).uniqueResult();

        }
    }

    @Override
    public List<File> getAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("Select distinct f from File f ").list();
        }
    }

    @Override
    public File save(File file) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(file);
        session.getTransaction().commit();
        session.close();
        return file;
    }

    @Override
    public File update(File file) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(file);
        session.getTransaction().commit();
        session.close();
        return file;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        File file = session.get(File.class, id);
        session.delete(file);
        session.getTransaction().commit();
        session.close();
    }
}
