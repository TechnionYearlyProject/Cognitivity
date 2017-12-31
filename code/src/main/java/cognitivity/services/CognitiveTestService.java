package cognitivity.services;

import cognitivity.entities.CognitiveTest;
import cognitivity.dao.RepositorySearchResult;
import cognitivity.dao.CognitiveTestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for cognitive test related operations.
 *
 */

@Service
public class CognitiveTestService extends AbstractService<CognitiveTest> {

    @Autowired
    public CognitiveTestService(CognitiveTest test) {
        super(test);
    }


    /**
     * Save a CognitiveTest for a test manager (admin).
     *
     * @param t - The cognitive test to be saved.
     * @param testManagerID - The test manager's id.
     *
     * @return - The saved CognitiveTest.
     *
     * This will be used in conjunction with the POST HTTP method.
     * */
    public CognitiveTest createTestForTestManager(CognitiveTestDAO t, long testManagerID) {
        return null;
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param testID - The test id to update.
     * @param t - The cognitive test to be updated.
     * @param testManagerID - The test manager's id.
     *
     * @return - The updated CognitiveTest.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public CognitiveTest updateTestForTestManager(long testID, CognitiveTestDAO t, long testManagerID) {
        return null;
    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     *
     * @param testID - The test id to delete.
     * @param testManagerID (optional - null if none) - The test manager's id.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestForTestManager(long testID, long  testManagerID) {

    }


    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param testManagerID - The test manager's id.
     *
     * @return - All tests the test manager has created.
     * */
    public RepositorySearchResult<CognitiveTest> findTestsForTestManager(long testManagerID) {
        return null;
    }

    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param testID - The test id to find.
     * @param testManagerID - The test manager's id.
     *
     * @return - The test the test manager has created with the given id.
     * */
    public CognitiveTest findTestForTestManagerById(long testID, long testManagerID) {
        return null;
    }
}
