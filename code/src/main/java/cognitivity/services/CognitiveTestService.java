package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
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
        CognitiveTestDAO dao = new CognitiveTestDAO();
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
        CognitiveTestDAO dao = new CognitiveTestDAO();
        dao.update(test);
    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     *
     * @param test - The test to delete.
     *               <p>
     *               This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestForTestManager(CognitiveTest test) {
        CognitiveTestDAO dao = new CognitiveTestDAO();
        dao.delete(test.getId());
    }


    /**
     * Find a specific cognitive test.
     *
     * @param testID - The test id to find.
     * @return - The test with the corresponding ID if it exists, null otherwise.
     */
    public CognitiveTest findTestById(long testID) {
        CognitiveTestDAO dao = new CognitiveTestDAO();
        return dao.get(testID);
    }

    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param manager - The manager.
     * @return - The test the test manager has created with the given id.
     */
    public List<CognitiveTest> findTestsForTestManager(TestManager manager) {
        CognitiveTestDAO dao = new CognitiveTestDAO();
        return dao.getCognitiveTestOfManager(manager);
    }


    /**
     * Find all the blocks in a given test.
     *
     * @param test - the given test
     * @return a list of all of the blocks in the test.
     */
    public List<TestBlock> getTestBlocksForTest(CognitiveTest test) {
        CognitiveTestDAO dao = new CognitiveTestDAO();
        return dao.getTestBlocks(test);
    }

    /**
     * Find all cognitive test questions in a test.
     *
     * @param test    - The test.
     * @return - All questions the test has.
     */
    public List<TestQuestion> getTestQuestionsForTest(CognitiveTest test) {
        CognitiveTestDAO dao = new CognitiveTestDAO();
        return dao.getTestQuestions(test);
    }

}