package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test subject related operations.
 *
 */

@Service
public class TestSubjectService {

    /**
     * Save a TestSubject.
     *
     * @param name - The test subject name.
     * @param ipAddress - The ip adress of the test subject.
     * @param browser - The browser the test subject was using.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public void createTestSubject(String name, Integer ipAddress, String browser) {
        TestSubject subject = new TestSubject(name,ipAddress,browser);
        TestSubjectDAO dao = new TestSubjectDAO();
        dao.add(subject);
    }

    /**
     * Update a TestSubject.
     *
     * @param subject - The test subject to update.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestSubject(TestSubject subject) {
        TestSubjectDAO dao = new TestSubjectDAO();
        dao.update(subject);
    }

    /**
     * Delete a TestSubject.
     *
     * @param testSubjectId - The test subject's id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestSubject(long testSubjectId) {
        TestSubjectDAO dao = new TestSubjectDAO();
        dao.delete(testSubjectId);
    }


    /**
     * Find a test subject by its id.
     *
     * @param testSubjectId - The test subject's id.
     * @return - the test subject found.
     */
    public TestSubject findTestSubject(long testSubjectId) {
        TestSubjectDAO dao = new TestSubjectDAO();
        return dao.get(testSubjectId);
    }

    /**
     * Find all answers given by a specific test subject
     *
     * @param subjectId - the test subject Id.
     * @return - all the answers the test subject gave.
     */
    public List<TestAnswer> findAllTestSubjectAnswers(long subjectId){
        TestSubjectDAO dao = new TestSubjectDAO();
        return dao.getSubjectAnswers(subjectId);
    }
    /**
     * Find all test subjects who took a cognitive a test.
     *
     * @param test - The test.
     * @return - all test subjects who took the cognitive test.
     */
    public List<TestSubject> findTestSubjectsWhoParticipatedInTest(CognitiveTest test) {
        return null;
    }

    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> findTestSubjectsWhoParticipatedInTest(long testId){
        TestSubjectDAO dao = new TestSubjectDAO();
        return dao.getTestSubjectsWhoParticipatedInTest(testId);
    }

}
