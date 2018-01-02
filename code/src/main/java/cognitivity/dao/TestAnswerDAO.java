package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Get all the test answers a subject has given.
     *
     * @param subject - The subject whose answers we are looking for
     *
     * @return - A list of all the answers by the subject
     */
    public List<TestAnswer> getAllTestSubjectAnswers(TestSubject subject){
        return null;
    }

    /**
     * Get all the test answers of a subject from a specific test.
     *
     * @param subject - The subject whose answers we are looking for.
     * @param test - The test from which we want the answers.
     *
     * @return - All the relevant answers from the test.
     */
    public List<TestAnswer> getAllTestSubjectAnswersInTest(TestSubject subject, CognitiveTest test){
        return null;
    }

    /**
     * Get all the answers for the given test question.
     *
     * @param question - The question whose answers we are looking for.
     * @return - A list of all test answers relating to the given question.
     */
    public List<TestAnswer> getAllTestAnswerForAQuestion(TestQuestion question){
        return null;
    }
}
