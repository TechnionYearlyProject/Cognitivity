package cognitivity.dao;

import cognitivity.entities.TestQuestion;

/**
 * Data Access Object for TestQuestion object
 */
public class TestQuestionDAO extends AbstractDAO<TestQuestion> {

    public TestQuestion get(Long id){
        return super.get(id, TestQuestion.class);
    }

    public void delete(Long id){
        super.delete(id, TestQuestion.class);
    }
}
