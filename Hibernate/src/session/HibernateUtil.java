package session;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Transaction currentTransaction;

    private static SessionFactory buildSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        } else {
            return configureSessionFactory();
        }
    }

    public static SessionFactory configureSessionFactory() {
        try {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            cfg.addAnnotatedClass(model.ClientModel.class);
            cfg.addAnnotatedClass(model.CoachModel.class);
            cfg.addAnnotatedClass(model.TrainingModel.class);
            cfg.addAnnotatedClass(model.Place.class);
            cfg.addAnnotatedClass(model.KindOfTraining.class);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Transaction getTransaction() {
        return currentTransaction;
    }

    public static Session openSession() {
        buildSessionFactory();
        return sessionFactory.openSession();
    }

    public static Session openSessionWithTransaction() {
        buildSessionFactory();
        Session currentSession = sessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();

        return currentSession;
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ex) {
                System.err.println("Couldn't close SessionFactory: " + ex);
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ex) {
                System.err.println("Couldn't close Session" + ex);
            }
        }
    }

    public static void closeSessionWithTransaction(Session session) {
        if (session != null && currentTransaction != null) {
            try {
                currentTransaction.commit();
                session.close();
            } catch (HibernateException ex) {
                System.err.println("Couldn't close Session" + ex);
            }
        }
    }
}
