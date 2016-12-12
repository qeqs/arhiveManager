package dao;

import entities.Entity;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    public void insert(T value) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(value);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Insert error");
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }

    }

    public void update(T value) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(value);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Update error");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void delete(T value) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(value);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Delete error");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public List<T> getAll() {
        Session session = null;
        List entities = new ArrayList<T>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            entities = session.createCriteria(this.getEntityClass()).list();
        } catch (Exception e) {
            System.out.println("Select error");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return entities;

    }

    public T getById(int id) {
        Session session = null;
        T value = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Class cl=getEntityClass();
            value = (T)session.load(cl, id);
        } catch (Exception e) {
            System.out.println("Select by ID error");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return value;
    }




    private Class getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
