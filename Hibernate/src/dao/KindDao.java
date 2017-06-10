package dao;

import java.util.List;
import model.KindOfTraining;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateUtil;

public class KindDao implements DaoInterface<KindOfTraining, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public KindDao() {
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
    public void create(KindOfTraining entity) {
        getSession().save(entity);
    }
    
    @Override
    public void createOrUpdate(KindOfTraining entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void update(KindOfTraining entity) {
        getSession().update(entity);
    }

    @Override
    public KindOfTraining findById(Integer id) {
        KindOfTraining kind = (KindOfTraining) getSession().get(KindOfTraining.class, id);
        return kind;
    }

    @Override
    public void delete(KindOfTraining entity) {
       getSession().delete(entity);
    }

    @Override
    public List<KindOfTraining> findAll() {
        Query query = getSession().createSQLQuery("SELECT * FROM kinds").addEntity(KindOfTraining.class);
        List<KindOfTraining> kind = query.list();
        //List<KindOfTraining> kind = (List<KindOfTraining>) getSession().createQuery("FROM model.KindOfTraining").list();
        return kind;
    }

    @Override
    public void deleteAll() {
        List<KindOfTraining> entityList = findAll();
        for (KindOfTraining entity : entityList) {
            delete(entity);
        }
    }

}
