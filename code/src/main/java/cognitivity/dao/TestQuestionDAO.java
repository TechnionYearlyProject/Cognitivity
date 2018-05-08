package cognitivity.dao;

import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;

import java.util.List;
/**
 * Created by Guy on 20/1/18.
 */
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

    /**
     * Get the link to the picture of the given question.
     *
     * @param questionID - the id of the given question
     * @return - The link to the picture
     */
    public String findPictureLinkPerQuestion(long questionID);

}
