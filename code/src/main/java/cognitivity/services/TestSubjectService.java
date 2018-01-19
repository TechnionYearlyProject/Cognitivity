package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
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


    private TestSubjectDAO dao;

    @Autowired
    public TestSubjectService(TestSubjectDAO dao) {
        this.dao = dao;
    }
    /**
     * Save a TestSubject.
     *
     * @param testSubject - The TestSubject to be saved
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestSubject createTestSubject(TestSubject testSubject)throws DBException {
        try {
            dao.add(testSubject);
            return testSubject;
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
        }
    }

    /**
     * Update a TestSubject.
     *
     * @param subject - The test subject to update.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestSubject(TestSubject subject)throws DBException {
        try {
            dao.update(subject);
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
        }
    }

    /**
     * Delete a TestSubject.
     *
     * @param testSubjectId - The test subject's id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestSubject(long testSubjectId)throws DBException {
        try {
            dao.delete(testSubjectId);
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
        }
    }


    /**
     * Find a test subject by its id.
     *
     * @param testSubjectId - The test subject's id.
     * @return - the test subject found.
     */
    public TestSubject findTestSubject(long testSubjectId) {
        return dao.get(testSubjectId);
    }

    /**
     * Find all answers given by a specific test subject
     *
     * @param subjectId - the test subject Id.
     * @return - all the answers the test subject gave.
     */
    public List<TestAnswer> findAllTestSubjectAnswers(long subjectId){
        return dao.getSubjectAnswers(subjectId);
    }


    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> findTestSubjectsWhoParticipatedInTest(long testId){
        return dao.getTestSubjectsWhoParticipatedInTest(testId);
    }

}
