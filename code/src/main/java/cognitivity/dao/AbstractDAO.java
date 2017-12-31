package cognitivity.dao;

import cognitivity.entities.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;




public abstract class AbstractDAO <DataType extends AbstractEntity> {
    // creates the objects which will be used to create hibernate objects
    // the bean that will be autowired here will be the one with the hibernate setting
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Data Access Object, implements basic CRUD logic
     */
    // returns CognitiveTest object by its id
    @Transactional(readOnly = true)
    protected DataType get(Long id, Class<DataType> DataTypeClass){
        Session session = sessionFactory.getCurrentSession();
        DataType data = session.get(DataTypeClass, id);
        return data;
    }

    // updates CognitiveTest object
    @Transactional
    public void update(DataType data){
        Session session = sessionFactory.getCurrentSession();
        session.merge(data);
    }

    // add CognitiveTest object to db
    @Transactional
    public void add(DataType data){
        Session session = sessionFactory.getCurrentSession();
        session.save(data);
    }

    // remove CognitiveTest by its id
    @Transactional
    protected void delete(Long id, Class<DataType> dataTypeClass){
        Session session = sessionFactory.getCurrentSession();
        DataType data = get(id, dataTypeClass);
        session.delete(data);
    }
}
