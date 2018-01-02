package cognitivity.services;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestManager;
import cognitivity.dao.TestManagerDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Business service for test manager related operations.
 *
 */

@Service
public class TestManagerService extends AbstractService {


    public TestManagerService(TestManager manager) {
    }

    public TestManagerService() {

    }

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
        TestManagerDAO dao = new TestManagerDAO();
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
        TestManagerDAO dao = new TestManagerDAO();
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
        TestManagerDAO dao = new TestManagerDAO();
        dao.delete(testManagerId);
    }


    /**
     * Find a test manager by its id.
     *
     * @param testManagerId - The test manager's id.
     * @return - the test manager found.
     */
    public TestManager findTestManager(long testManagerId) {
        TestManagerDAO dao = new TestManagerDAO();
        return dao.get(testManagerId);
    }

    /**
     * Find test manager who created a test with the given id.
     *
     * @param testId - The test id the test manager created.
     * @return - the test manager that created the test.
     * # Could return empty search result
     */
    public List<TestManager> findTestManagerByCreatedTest(long testId) {
        return null;
    }




    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param manager - The test manager.
     * @param tests   - all the tests in the database.
     * @return - All tests the test manager has created.
     */
    public List<CognitiveTest> findTestsForTestManager(TestManager manager, List<CognitiveTest> tests) {
        return null;
    }


}
