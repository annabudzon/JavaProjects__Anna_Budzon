package dao;

import java.util.List;
import model.ClientModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateUtil;

public class ClientDao implements DaoInterface<ClientModel, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public ClientDao() {
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
    public void create(ClientModel entity) {
        getSession().save(entity);
    }

    @Override
    public void createOrUpdate(ClientModel entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void update(ClientModel entity) {
        getSession().update(entity);
    }

    @Override
    public ClientModel findById(Integer id) {
        ClientModel client = (ClientModel) getSession().get(ClientModel.class, id);
        return client;
    }

    @Override
    public void delete(ClientModel entity) {
        getSession().delete(entity);
    }

    @Override
    public List<ClientModel> findAll() {
        Query query = getSession().createSQLQuery("SELECT * FROM client").addEntity(model.ClientModel.class);
        List<ClientModel> clients = query.list();
        //List<ClientModel> client = (List<ClientModel>) getSession().createQuery("FROM model.ClientModel").list();
        return clients;
    }

    @Override
    public void deleteAll() {
        List<ClientModel> entityList = findAll();
        for (ClientModel entity : entityList) {
            delete(entity);
        }
    }

}
