package cognitivity.dao;

import cognitivity.entities.TestSubject;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for TestQuestion object
 */
@Repository
public class TestSubjectDAO extends AbstractDAO<TestSubject> {
    public TestSubject get(Long id){
        return super.get(id, TestSubject.class);
    }

    public void delete(Long id){
        super.delete(id, TestSubject.class);
    }
}
