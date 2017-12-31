package cognitivity.services;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.entities.TestSubject;
import cognitivity.dao.TestSubjectDAO;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test subject related operations.
 *
 */

@Service
public class TestSubjectService extends AbstractService<TestSubject>  {


    protected TestSubjectService(TestSubject subject) {
        super(subject);
    }

    /**
     * Save a TestSubject.
     *
     * @param s - The test subject to be saved.
     * @return - The saved TestSubject.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestSubject createTestSubject(TestSubjectDAO s) {
        return null;
    }

    /**
     * Update a TestSubject.
     *
     * @param s - The cognitive test subject to be updated.
     * @param testSubjectId - The test subject's id.
     *
     * @return - The updated TestSubject.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public TestSubject updateTestForTestManager(long testSubjectId, TestSubjectDAO s) {
        return null;
    }

    /**
     * Delete a TestSubject.
     *
     * @param testSubjectId - The test subject's id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestSubject(long testSubjectId) {

    }


    /**
     * Find a test subject by its id.
     *
     * @param testSubjectId - The test subject's id.
     * @return - the test subject found.
     */
    public TestSubject findTestSubject(long testSubjectId) {
        return null;
    }

    /**
     * Find all test subjects who took a cognitive a test by the test id.
     *
     * @param testID - The test id to find all test subjects by.
     * @return - all test subjects who took a cognitive a test by the test id.
     */
    public RepositorySearchResult<TestSubject> findTestSubjectsWhoParticipatedInTest(long testID) {
        return null;
    }

}
