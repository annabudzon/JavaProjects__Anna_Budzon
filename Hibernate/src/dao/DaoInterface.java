package dao;

import java.io.Serializable;
import java.util.List;

public interface DaoInterface<T, Integer extends Serializable> {

    public void create(T entity);
    
    public void createOrUpdate(T entity);

    public void update(T entity);

    public T findById(Integer id);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll();
}
