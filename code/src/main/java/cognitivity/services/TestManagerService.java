package cognitivity.services;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.entities.TestManager;
import cognitivity.dao.TestManagerDAO;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test manager related operations.
 *
 */

@Service
public class TestManagerService extends AbstractService<TestManager> {


    protected TestManagerService(TestManager manager) {
        super(manager);
    }

    /**
     * Save a TestManager.
     *
     * @param m - The test manager to be saved.
     * @return - The saved TestManager.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestManager createTestManager(TestManagerDAO m) {
        return null;
    }

    /**
     * Update a TestManager.
     *
     * @param m             - The test manager to be updated.
     * @param testManagerId - The test manager's id.
     * @return - The updated TestManager.
     * <p>
     * This will be used in conjunction with the PUT HTTP method.
     */
    public TestManager updateTestManager(long testManagerId, TestManagerDAO m) {
        return null;
    }

    /**
     * Delete a TestManager.
     *
     * @param testManagerId - The test manager's id to delete.
     *                      <p>
     *                      This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestManager(long testManagerId) {

    }


    /**
     * Find a test manager by its id.
     *
     * @param testManagerId - The test manager's id.
     * @return - the test manager found.
     */
    public TestManager findTestManager(long testManagerId) {
        return null;
    }

    /**
     * Find test manager who created a test with the given id.
     *
     * @param testID - The test id the test manager created.
     * @return - the test manager that created the test.
     * # Could return empty search result
     */
    public RepositorySearchResult<TestManager> findTestManagerByCreatedTest(long testID) {
        return null;
    }

}
