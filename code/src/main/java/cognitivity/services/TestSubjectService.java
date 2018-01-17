package cognitivity.services;

import cognitivity.dao.TestManagerDAOimpl;
import cognitivity.dao.TestSubjectDAO;
import cognitivity.dao.TestSubjectDAOimpl;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test subject related operations.
 *
 */

@Service
public class TestSubjectService {


    private TestSubjectDAOimpl dao;

    @Autowired
    public TestSubjectService(TestSubjectDAOimpl dao) {
        this.dao = dao;
    }
    /**
     * Save a TestSubject.
     *
     * @param name - The test subject name.
     * @param ipAddress - The ip adress of the test subject.
     * @param browser - The browser the test subject was using.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestSubject createTestSubject(String name, Integer ipAddress, String browser) {
        TestSubject subject = new TestSubject(name,ipAddress,browser);
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        dao.add(subject);
        return subject;
    }

    /**
     * Update a TestSubject.
     *
     * @param subject - The test subject to update.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestSubject(TestSubject subject) {
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
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
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        dao.delete(testSubjectId);
    }


    /**
     * Find a test subject by its id.
     *
     * @param testSubjectId - The test subject's id.
     * @return - the test subject found.
     */
    public TestSubject findTestSubject(long testSubjectId) {
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        return dao.get(testSubjectId);
    }

    /**
     * Find all answers given by a specific test subject
     *
     * @param subjectId - the test subject Id.
     * @return - all the answers the test subject gave.
     */
    public List<TestAnswer> findAllTestSubjectAnswers(long subjectId){
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        return dao.getSubjectAnswers(subjectId);
    }

    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> findTestSubjectsWhoParticipatedInTest(long testId){
//        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        return dao.getTestSubjectsWhoParticipatedInTest(testId);
    }

}
