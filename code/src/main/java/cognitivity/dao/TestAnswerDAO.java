package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for TestAnswer object
 */
@Repository
public class TestAnswerDAO extends AbstractDAO<TestAnswer> {

    public TestAnswer get(Long id){
        return super.get(id, TestAnswer.class);
    }

    public void delete(Long id){
        super.delete(id, TestAnswer.class);
    }
}
