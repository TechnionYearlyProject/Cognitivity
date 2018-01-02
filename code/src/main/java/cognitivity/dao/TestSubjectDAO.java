package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Get all the answers for the given test subject
     *
     * @param subject - the test subject.
     * @return - A list of all the answers the test subject has given.
     */
    public List<TestAnswer> getAllTestSubjectAnswers(TestSubject subject){
        return null;
    }

    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> getTestSubjectsWhoParticipatedInTest(long testId){
        return null;
    }
}
