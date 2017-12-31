package cognitivity.dao;

import cognitivity.entities.TestQuestion;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for TestQuestion object
 */
@Repository
public class TestQuestionDAO extends AbstractDAO<TestQuestion> {

    public TestQuestion get(Long id){
        return super.get(id, TestQuestion.class);
    }

    public void delete(Long id){
        super.delete(id, TestQuestion.class);
    }
}
