package cognitivity.services;

import cognitivity.dao.CognitiveTestDAOimpl;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for cognitive test related operations.
 *
 */

@Service
public class CognitiveTestService {


    /**
     * Create a cognitive test, and save it in the DB.
     *
     * @param name - The name of the test.
     * @param manager - The manager of the test.
     * @param state - The state of the test.
     * @param numberOfQuestions - The number of questions in the test.
     * @return
     */
    public CognitiveTest createTestForTestManager(String name, TestManager manager, Integer state, Integer numberOfQuestions) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        CognitiveTest t = new CognitiveTest(name,manager,state,numberOfQuestions);
        dao.add(t);
        return t;
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param test           - The cognitive test to be updated.
     * <p>
     * This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestForTestManager(CognitiveTest test) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        dao.update(test);
    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     *
     * @param testID - The test id to delete.
     *               <p>
     *               This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestForTestManager(long testID) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        dao.delete(testID);
    }


    /**
     * Find a specific cognitive test.
     *
     * @param testID - The test id to find.
     * @return - The test with the corresponding ID if it exists, null otherwise.
     */
    public CognitiveTest findTestById(long testID) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        return dao.get(testID);
    }

    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The manager.
     * @return - The test the test manager has created with the given id.
     */
    public List<CognitiveTest> findTestsForTestManager(long managerId) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        return dao.getCognitiveTestOfManager(managerId);
    }


    /**
     * Find all the blocks in a given test.
     *
     * @param testId - the given test id
     * @return a list of all of the blocks in the test.
     */
    public List<TestBlock> getTestBlocksForTest(long testId) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        return dao.getTestBlocks(testId);
    }

    /**
     * Find all cognitive test questions in a test.
     *
     * @param testId    - The test Id.
     * @return - All questions the test has.
     */
    public List<TestQuestion> getTestQuestionsForTest(long testId) {
        CognitiveTestDAOimpl dao = new CognitiveTestDAOimpl();
        return dao.getTestQuestions(testId);
    }

}