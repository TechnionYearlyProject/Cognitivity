package cognitivity.dao;

import cognitivity.entities.TestAnswer;

/**
 * Data Access Object for TestAnswer object
 */
public class TestAnswerDAO extends AbstractDAO<TestAnswer> {

    public TestAnswer get(Long id){
        return super.get(id, TestAnswer.class);
    }

    public void delete(Long id){
        super.delete(id, TestAnswer.class);
    }
}
