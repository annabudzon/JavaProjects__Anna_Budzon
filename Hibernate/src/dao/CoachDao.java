package dao;

import java.util.List;
import model.CoachModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateUtil;

public class CoachDao implements DaoInterface<CoachModel, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public CoachDao() {
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

    public void closeSessionWithTransaction() {
        HibernateUtil.closeSessionWithTransaction(this.currentSession);
    }

    @Override
    public void create(CoachModel entity) {
        getSession().save(entity);
    }

    @Override
    public void createOrUpdate(CoachModel entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void update(CoachModel entity) {
        getSession().update(entity);
    }

    @Override
    public CoachModel findById(Integer id) {
        CoachModel coach = (CoachModel) getSession().get(CoachModel.class, id);
        return coach;
    }

    @Override
    public void delete(CoachModel entity) {
        getSession().delete(entity);
    }

    @Override
    public List<CoachModel> findAll() {
        Query query = getSession().createSQLQuery("SELECT * FROM coach").addEntity(CoachModel.class);
        List<CoachModel> coach = query.list();
        //List<CoachModel> coach = (List<CoachModel>) getSession().createQuery("FROM model.CoachModel").list();
        return coach;
    }

    @Override
    public void deleteAll() {
        List<CoachModel> entityList = findAll();
        for (CoachModel entity : entityList) {
            delete(entity);
        }
    }

}
