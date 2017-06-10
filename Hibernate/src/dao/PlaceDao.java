package dao;

import java.util.List;
import model.Place;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateUtil;

public class PlaceDao implements DaoInterface<Place, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public PlaceDao() {
    }

    public Session getSession() {
        return currentSession;
    }

    public Transaction getTransaction() {
        return currentTransaction;
    }

    public void setSession() {
        this.currentSession = HibernateUtil.openSession();
    }

    public void setSessionWithTransaction() {
        this.currentSession = HibernateUtil.openSessionWithTransaction();
    }

    public void setTransaction() {
        this.currentTransaction = HibernateUtil.getTransaction();
    }

    public void closeSession() {
        HibernateUtil.close(this.currentSession);
    }

    public void closeSessionWithTransaction(Session session) {
        HibernateUtil.closeSessionWithTransaction(this.currentSession);
    }

    @Override
    public void create(Place entity) {
        getSession().save(entity);
    }
    
    @Override
    public void createOrUpdate(Place entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void update(Place entity) {
        getSession().update(entity);
    }

    @Override
    public Place findById(Integer id) {
        Place client = (Place) getSession().get(Place.class, id);
        return client;
    }

    @Override
    public void delete(Place entity) {
        getSession().delete(entity);
    }

    @Override
    public List<Place> findAll() {
        Query query = getSession().createSQLQuery("SELECT * FROM places").addEntity(Place.class);
        List<Place> place = query.list();
        //List<Place> place = (List<Place>) getSession().createQuery("FROM model.Place").list();
        return place;
    }

    @Override
    public void deleteAll() {
        List<Place> entityList = findAll();
        for (Place entity : entityList) {
            delete(entity);
        }
    }
}
