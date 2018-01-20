package cognitivity.dao;

import cognitivity.entities.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public abstract class AbstractDAO<DataType extends AbstractEntity> {
    // creates the objects which will be used to create hibernate objects
    // the bean that will be autowired here will be the one with the hibernate setting
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * search the object in the DB
     *
     * @throws org.hibernate.HibernateException
     * @param id the id of the requested object
     * @param DataTypeClass the the dataType class
     *                      (this function should be used by the class implementations)
     * @return null in case could not find the object
     *
     */
    @Transactional(readOnly = true)
    protected DataType get(Long id, Class<DataType> DataTypeClass) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(DataTypeClass, id);
    }

    /**
     * updates the object in the database
     * if the object already in the database, the function updates it,
     * else, the function creates it.
     *
     * @throws org.hibernate.HibernateException
     * @param data the object to update
     * @return the object id of the object
     *
     */
    public long update(DataType data) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(data);
        return data.getId();
    }

    /**
     * adds new object to the db
     *
     * @throws org.hibernate.HibernateException
     * @param data the object to save
     * @return the object id
     *
     */
    public long add(DataType data) {
        Session session = sessionFactory.getCurrentSession();
        session.save(data);
        return data.getId();
    }

    /**
     * deletes value from the db
     *
     * @throws org.hibernate.HibernateException
     * @param id the id of the object need to be deleted
     * @param dataTypeClass the the dataType class
     *                      (this function should be used by the class implementations)
     *
     */
    protected void delete(Long id, Class<DataType> dataTypeClass) {
        Session session = sessionFactory.getCurrentSession();
        DataType data = get(id, dataTypeClass);
        session.delete(data);
    }
}
