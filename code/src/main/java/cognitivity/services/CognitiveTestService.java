package cognitivity.services;

import cognitivity.dao.CognitiveTestRepository;
import cognitivity.dto.CognitiveTestDTO;
import cognitivity.model.CognitiveTest;
import cognitivity.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for cognitive test related operations.
 *
 */

@Service
public class CognitiveTestService {

    private final CognitiveTestRepository cognitiveTestRepository;

    @Autowired
    public CognitiveTestService(CognitiveTestRepository cognitiveTestRepository) {
        this.cognitiveTestRepository = cognitiveTestRepository;
    }


    /**
     * Save a CognitiveTest for a test manager (admin).
     *
     * @param t - The cognitive test to be saved.
     * @param testManagerID - The test manager's id.
     *
     * @return - The saved CognitiveTest.
     *
     * This will use the POST HTTP method.
     * */
    public CognitiveTest createTestForTestManager(CognitiveTestDTO t, String testManagerID) {
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
     * This will use the PUT HTTP method.
     * */
    public CognitiveTest updateTestForTestManager(String testID, CognitiveTestDTO t, String testManagerID) {
        return null;
    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     *
     * @param testID - The test id to delete.
     * @param testManagerID (optional - null if none) - The test manager's id.
     *
     * This will use the DELETE HTTP method.
     * */
    public void deleteTestForTestManager(String testID, String testManagerID) {

    }


    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param testManagerID - The test manager's id.
     *
     * @return - All tests the test manager has created.
     * */
    public SearchResult<CognitiveTest> findTestsForTestManager(String testManagerID) {
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
    public CognitiveTest findTestForTestManagerById(String testID, String testManagerID) {
        return null;
    }
}
