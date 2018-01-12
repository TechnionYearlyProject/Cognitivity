package cognitivity.services;

import cognitivity.dao.CognitiveTestDAOimpl;
import cognitivity.dao.TestManagerDAOimpl;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test manager related operations.
 *
 */

@Service
public class TestManagerService {

    /**
     * Save a TestManager.
     *
     * @param name - The name of the test manager.
     * @param password - The password of the test manager.
     * @return - The saved TestManager.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestManager createTestManager(String name, String password) {
        TestManagerDAOimpl dao = new TestManagerDAOimpl();
        TestManager manager = new TestManager(name,password);
        dao.add(manager);
        return manager;
    }

    /**
     * Update a TestManager.
     *
     * @param manager - The test manager to be updated.
     * <p>
     * This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestManager(TestManager manager) {
        TestManagerDAOimpl dao = new TestManagerDAOimpl();
        dao.update(manager);
    }

    /**
     * Delete a TestManager.
     *
     * @param testManagerId - The test manager's id to delete.
     *                      <p>
     *                      This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestManager(long testManagerId) {
        TestManagerDAOimpl dao = new TestManagerDAOimpl();
        dao.delete(testManagerId);
    }


    /**
     * Find a test manager by its id.
     *
     * @param testManagerId - The test manager's id.
     * @return - the test manager found.
     */
    public TestManager findTestManager(long testManagerId) {
        TestManagerDAOimpl dao = new TestManagerDAOimpl();
        return dao.get(testManagerId);
    }

    /**
     * Find test manager who created a test with the given id.
     *
     * @param testId - The test id the test manager created.
     * @return - the test manager that created the test.
     * # Could return empty search result
     */
    public TestManager findTestManagerByCreatedTest(long testId) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        CognitiveTest test = dao.get(testId);
        long managerId = test.getManager().getId();
        TestManagerDAOimpl managerDAO = new TestManagerDAOimpl();
        return managerDAO.get(managerId);
    }




    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The test manager Id.
     * @return - All tests the test manager has created.
     */
    public List<CognitiveTest> findTestsForTestManager(long managerId) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        return dao.getCognitiveTestOfManager(managerId);
    }


}
