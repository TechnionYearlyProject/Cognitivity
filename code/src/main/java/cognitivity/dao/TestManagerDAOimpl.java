package cognitivity.dao;

import cognitivity.entities.TestManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Guy on 20/1/18.
 *
 * Data Access Object for TestManager object
 * @Note! API documentation is in the Interfaces
 *
 */
@Repository
public class TestManagerDAOimpl extends AbstractDAO<TestManager> implements TestManagerDAO {

    public TestManager get(Long id) {
        return super.get(id, TestManager.class);
    }

    public void delete(Long id) {
        super.delete(id, TestManager.class);
    }

    public long getIdFromEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select T.id from TestManager T where T.email = :email";
        Query<Long> integerQuery = session.createQuery(queryString, Long.class);
        integerQuery.setParameter("email", email);
        return integerQuery.getSingleResult();
    }

    @Override
    public boolean managerWithIdExists(long managerId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestManager T where T.id LIKE :managerId";
        Query<TestManager> query = session.createQuery(queryString, TestManager.class);
        query.setParameter("managerId", managerId);
        List<TestManager> res = query.getResultList();
        return !res.isEmpty();
    }
}
