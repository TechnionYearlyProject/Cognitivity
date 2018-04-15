package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
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
    public TestWrapper createTestForTestManager(TestWrapper cognitiveTest) throws DBException {
        try {
            long testId = dao.add(cognitiveTest.innerTest());
            List<BlockWrapper> blocks = cognitiveTest.getBlocks();
            if (blocks != null) {
                CognitiveTest test = cognitiveTest.innerTest();
                for (BlockWrapper block : blocks) {
                    test.setId(testId);
                    block.setCognitiveTest(test);
                    long blockId = blockDAO.add(block.innerBlock(test.getId()));
                    TestBlock testBlock = block.innerBlock(test.getId());
                    testBlock.setId(blockId);
                    if (block.getQuestions() != null) {
                        for (TestQuestion question : block.getQuestions()) {
                            question.setTestManager(cognitiveTest.getTestManager());
                            question.setCognitiveTest(test);
                            question.setTestBlock(testBlock);
                            questionDAO.add(question);
                        }
                    }
                }
            }
            cognitiveTest.setId(testId);
            return cognitiveTest;
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.SAVE);
        }
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param test - The cognitive test to be updated.
     *             <p>
     *             This will be used in conjunction with the PUT HTTP method.
     * @Returns the new allocated id for the test
     *
     */
    public TestWrapper updateTestForTestManager(TestWrapper test) throws DBException {
        try {
            // we delete the test and add it once again to
            // remove all the dependencies in the DB(questions and blocks that connected to the test)
            // we need to do it because we get only the blocks that will be in the updated test(faster then remove
            // all the existing ones manually)
            System.out.println(test.innerTest().getId());
            dao.delete(test.innerTest().getId());
            return createTestForTestManager(test);
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.UPDATE);
        }

    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     * This method deletes all the blocks and questions of the test
     *
     * @param testID - The test id to delete.
     *               <p>
     *               This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestForTestManager(long testID) throws DBException {
        try {
            dao.delete(testID);
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.DELETE);
        }
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
    public List<TestWrapper> findTestsForTestManager(long managerId) throws DBException {
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
     * @return - All questions that the test has.
     */
    public List<TestQuestion> getTestQuestionsForTest(long testId) {
        return dao.getTestQuestions(testId);
    }

    // TODO !

    /**
     * Method for getting all tests with a specific description in notes field.
     *
     * @param notes - The notes files filter.
     * @return - All tests (wrapper) that their notes field contains the notes string parameter.
     */
    public List<TestWrapper> filterTestsByNotes(String notes) {
        return null;
    }

    // TODO !

    /**
     * Method for getting all tests of a specific project.
     *
     * @param projectFilter - The project test filter.
     * @return - All tests (wrapper) that their project field is the same as the projectFilter parameter.
     */
    public List<TestWrapper> filterTestsByProject(String projectFilter) { return null; }
}