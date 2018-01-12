package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;

import java.util.List;

public interface TestSubjectDAO {

    public TestSubject get(Long id);
    public void delete(Long id);
    public void add(TestSubject data);
    public void update(TestSubject data);

    /**
     * Get all the answers for the given test subject
     *
     * @param subjectId - the test subjects Id.
     * @return - A list of all the answers the test subject has given.
     */
    public List<TestAnswer> getSubjectAnswers(long subjectId);

    /**
     * Get all the test subject who participated in a given test.
     * subject participate in test if the subject answered at least one question in the test
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> getTestSubjectsWhoParticipatedInTest(long testId);

}
