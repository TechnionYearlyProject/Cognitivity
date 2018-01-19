package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
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
    private TestQuestionDAO questionDAO;

    @Autowired
    public CognitiveTestService(CognitiveTestDAO dao, TestBlockDAO blockDAO, TestQuestionDAO questionDAO) {
        this.dao = dao;
        this.blockDAO = blockDAO;
        this.questionDAO = questionDAO;

    }


    /**
     * Create a cognitive test, and save it in the DB.
     *
     * @param cognitiveTest - The cognitive test to be created
     * @return
     */
    public TestWrapper createTestForTestManager(TestWrapper cognitiveTest) {
        dao.add(cognitiveTest);
        List<BlockWrapper> blocks = cognitiveTest.getBlocks();
        if (blocks != null) {
            for (BlockWrapper block : blocks) {
                blockDAO.add(block);
                if (blockDAO.getAllBlockQuestions(block.getId()) != null) {
                    for (TestQuestion question : blockDAO.getAllBlockQuestions(block.getId())) {
                        questionDAO.add(question);
                    }
                }
            }
        }
        return cognitiveTest;
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param test - The cognitive test to be updated.
     *             <p>
     *             This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestForTestManager(TestWrapper test) {
        dao.update(test);
        if (test.getBlocks() != null) {
            for (BlockWrapper block : test.getBlocks()) {
                blockDAO.update(block);
                if (blockDAO.getAllBlockQuestions(block.getId()) != null) {
                    for (TestQuestion question : blockDAO.getAllBlockQuestions(block.getId())) {
                        questionDAO.update(question);
                    }
                }
            }
        }
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
        List<BlockWrapper> blocks = new ArrayList<BlockWrapper>();
        List<TestBlock> preWrapped = dao.getTestBlocks(testID);
        for (TestBlock block : preWrapped) {
            blocks.add(new BlockWrapper(blockDAO.getAllBlockQuestions(block.getId()), block));
        }
        return new TestWrapper(dao.get(testID), blocks);
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
        for (CognitiveTest test : preWrapped) {
            tests.add(findTestById(test.getId()));
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
        for (TestBlock block : preWrapped) {
            blocks.add(new BlockWrapper(blockDAO.getAllBlockQuestions(block.getId()), block));
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