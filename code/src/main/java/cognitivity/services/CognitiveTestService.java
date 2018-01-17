package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business service for cognitive test related operations.
 */

@Service
public class CognitiveTestService {

    private CognitiveTestDAO dao;
    private TestBlockDAO blockDAO;

    @Autowired
    public CognitiveTestService(CognitiveTestDAO dao, TestBlockDAO blockDAO) {
        this.dao = dao;
        this.blockDAO = blockDAO;

    }


    /**
     * Create a cognitive test, and save it in the DB.
     *
     * @param cognitiveTest     - The cognitive test to be created
     * @return
     */
    public TestWrapper createTestForTestManager(CognitiveTest cognitiveTest) {
        dao.add(cognitiveTest);
        return new TestWrapper(cognitiveTest,dao, blockDAO);
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param test - The cognitive test to be updated.
     *             <p>
     *             This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestForTestManager(CognitiveTest test) {
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
        dao.delete(testID);
    }


    /**
     * Find a specific cognitive test.
     *
     * @param testID - The test id to find.
     * @return - The test with the corresponding ID if it exists, null otherwise.
     */
    public TestWrapper findTestById(long testID) {
        return new TestWrapper(dao.get(testID), dao, blockDAO);
    }

    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The manager.
     * @return - The test the test manager has created with the given id.
     */
    public List<TestWrapper> findTestsForTestManager(long managerId) {
        List<TestWrapper> tests = new ArrayList<TestWrapper>();
        List<CognitiveTest> preWrapped = dao.getCognitiveTestOfManager(managerId);
        for (CognitiveTest test : preWrapped){
            tests.add(new TestWrapper(test,dao,blockDAO));
        }
        return tests;
    }


    /**
     * Find all the blocks in a given test.
     *
     * @param testId - the given test id
     * @return a list of all of the blocks in the test.
     */
    public List<BlockWrapper> getTestBlocksForTest(long testId) {
        List<TestBlock> preWrapped = dao.getTestBlocks(testId);
        List<BlockWrapper> blocks = new ArrayList<BlockWrapper>();
        for (TestBlock block : preWrapped){
            blocks.add(new BlockWrapper(blockDAO,block));
        }
        return blocks;
    }

    /**
     * Find all cognitive test questions in a test.
     *
     * @param testId - The test Id.
     * @return - All questions the test has.
     */
    public List<TestQuestion> getTestQuestionsForTest(long testId) {
        return dao.getTestQuestions(testId);
    }

}