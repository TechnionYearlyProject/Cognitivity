package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Get a list of all test answers for a given question.
     *
     * @param question - The question whose answers we want to get.
     * @return - A list of all answers to the question.
     */
    public List<TestAnswer> getAllRelevantAnswers(TestQuestion question){
        return null;
    }

    /**
     * Get all test questions from a given test.
     *
     * @param test - The test Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> getAllTestQuestionsFromATest(long test){
        return null;
    }

    /**
     * Get all test questions from a given manager.
     *
     * @param manager - The manager Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> getAllTestQuestionsFromAManager(long manager){
        return null;
    }
}
