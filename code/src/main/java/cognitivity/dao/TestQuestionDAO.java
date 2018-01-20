package cognitivity.dao;

import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;

import java.util.List;

public interface TestQuestionDAO {

    /**
     *
     * @Note! API documentation of get, delete, add and update is in the AbstractDAO
     *
     */
    public TestQuestion get(Long id);
    public void delete(Long id);
    public long add(TestQuestion data);
    public long update(TestQuestion data);

    /**
     * Get all test questions from a given manager.
     *
     * @param manager - The manager from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> getTestQuestionsFromAManager(TestManager manager);

}
