package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final ArhiveDAO arhivesDAO;
    private static final EntryDAO entryDAO;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        arhivesDAO = new ArhiveDAO();
        entryDAO = new EntryDAO();
    }

    public static ArhiveDAO getArhivesDAO() {
        return arhivesDAO;
    }

    public static EntryDAO getEntryDAO() {
        return entryDAO;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }



}
