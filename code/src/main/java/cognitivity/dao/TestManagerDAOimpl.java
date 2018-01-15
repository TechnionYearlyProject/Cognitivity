package cognitivity.dao;

import cognitivity.entities.TestManager;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for TestManager object
 */
@Repository
public class TestManagerDAOimpl extends AbstractDAO<TestManager> implements TestManagerDAO {

    public TestManager get(Long id) {
        return super.get(id, TestManager.class);
    }

    public void delete(Long id) {
        super.delete(id, TestManager.class);
    }
}
