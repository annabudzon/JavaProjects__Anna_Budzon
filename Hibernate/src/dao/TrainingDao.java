package dao;

import java.util.List;
import model.TrainingModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateUtil;

public class TrainingDao implements DaoInterface<TrainingModel, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public TrainingDao() {
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
    public void create(TrainingModel entity) {
        getSession().save(entity);
    }

    @Override
    public void createOrUpdate(TrainingModel entity) {
        getSession().saveOrUpdate(entity);
    }
    
    @Override
    public void update(TrainingModel entity) {
        getSession().update(entity);
    }

    @Override
    public TrainingModel findById(Integer id) {
        TrainingModel client = (TrainingModel) getSession().get(TrainingModel.class, id);
        return client;
    }

    @Override
    public void delete(TrainingModel entity) {
        getSession().delete(entity);
    }

    @Override
    public List<TrainingModel> findAll() {
        
        Query query = getSession().createSQLQuery("SELECT * FROM training").addEntity(TrainingModel.class);
        List<TrainingModel> training = query.list();
        //CriteriaQuery cq = session.getCriteriaBuilder().createQuery(Employee.class);
	//cq.from(Employee.class);
	//List employeeList = session.createQuery(cq).getResultList();
        //List<TrainingModel> training = (List<TrainingModel>) getSession().createQuery("FROM model.TrainingModel").list();
        return training;
    }

    @Override
    public void deleteAll() {
        List<TrainingModel> entityList = findAll();
        for (TrainingModel entity : entityList) {
            delete(entity);
        }
    }

}
