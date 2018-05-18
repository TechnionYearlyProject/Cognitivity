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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business service for cognitive test related operations.
 *
 * @Author - Pe'er
 * @Date - 2.2.18
 */

@Service
public class CognitiveTestService {

    private final static Logger logger = Logger.getLogger(CognitiveTestService.class);

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
     * Refactoring for code re-use in another service.
     */
    public static TestWrapper saveTestWrapperWithDaos(TestWrapper cognitiveTest,
                                                      CognitiveTestDAO cognitiveTestDAO,
                                                      TestBlockDAO testBlockDAO,
                                                      TestQuestionDAO testQuestionDAO) throws DBException {
        try {
            long testId = cognitiveTestDAO.add(cognitiveTest.innerTest());
            List<BlockWrapper> blocks = cognitiveTest.getBlocks();
            if (blocks != null) {
                CognitiveTest test = cognitiveTest.innerTest();
                for (BlockWrapper block : blocks) {
                    test.setId(testId);
                    block.setCognitiveTest(test);
                    long blockId = testBlockDAO.add(block.innerBlock(test.getId()));
                    TestBlock testBlock = block.innerBlock(test.getId());
                    testBlock.setId(blockId);
                    if (block.getQuestions() != null) {
                        for (TestQuestion question : block.getQuestions()) {
                            question.setTestManager(cognitiveTest.getTestManager());
                            question.setCognitiveTest(test);
                            question.setTestBlock(testBlock);
                            testQuestionDAO.add(question);
                        }
                    }
                }
            }
            cognitiveTest.setId(testId);
            logger.info("Successfully added test. testId = " + testId);
            return cognitiveTest;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to create a cognitive test and add it to the DB. Test ID: " + cognitiveTest.innerTest().getId(), e);
            throw new DBException(ErrorType.SAVE, cognitiveTest.getId());
        }
    }


    /**
     * Create a cognitive test, and save it in the DB.
     *
     * @param cognitiveTest - The cognitive test to be created
     * @return
     */
    public TestWrapper createTestForTestManager(TestWrapper cognitiveTest) throws DBException {
        return saveTestWrapperWithDaos(cognitiveTest, dao, blockDAO, questionDAO);
    }

    /**
     * Update a CognitiveTest for a test manager (admin).
     *
     * @param test - The cognitive test to be updated.
     *             <p>
     *             This will be used in conjunction with the PUT HTTP method.
     * @Return the new allocated id for the test
     */
    public TestWrapper updateTestForTestManager(TestWrapper test) throws DBException {
        deleteTestForTestManager(test.innerTest().getId());
        return createTestForTestManager(test);
    }

    /**
     * Delete a CognitiveTest for a test manager (admin).
     * This method deletes all the blocks and questions of the test
     *
     * @param testId- The test id to delete.
     *                <p>
     *                This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestForTestManager(long testId) throws DBException {
        try {
            dao.delete(testId);
            logger.info("Successfully deleted test. testId = " + testId);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to delete a test for test manager. Test ID: " + testId, e);
            throw new DBException(ErrorType.DELETE, testId);
        }
    }


    /**
     * Method for searching searching for a cognitive test by its id in the DB with all its question.
     *
     * @param testID - id of the test as its written in the database.
     *               The test wrapper to be returned should have all the blocks and questions
     *               that are related to (in the DB tables) to the test with the id, as it was returned
     *               by the findTestsForTestManagerWithoutQuestions (new) method.
     * @return - test wrapper with all questions and blocks, as described above.
     */
    public TestWrapper findTestById(long testID) throws DBException {
        try {
            List<BlockWrapper> blocks = new ArrayList<>();
            List<TestBlock> preWrapped = dao.getTestBlocks(testID);
            for (TestBlock block : preWrapped) {
                blocks.add(new BlockWrapper(blockDAO.getAllBlockQuestions(block.getId()), block));
            }
            logger.info("Successfully found test. testId = " + testID);
            return new TestWrapper(dao.get(testID), blocks);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find test. Test ID: " + testID, e);
            throw new DBException(ErrorType.GET, testID);
        }
    }

    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The manager.
     * @return - The test the test manager has created with the given id.
     */
    public List<TestWrapper> findTestsForTestManager(long managerId) throws DBException {
        List<TestWrapper> tests = new ArrayList<>();
        try {
            List<CognitiveTest> preWrapped = dao.getCognitiveTestOfManager(managerId);
            for (CognitiveTest test : preWrapped) {
                tests.add(findTestById(test.getId()));
            }
            logger.info("Successfully found tests for test manager. testId = " + managerId);
            return tests;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find tests for test manager. Test manager ID: " + managerId, e);
            throw new DBException(ErrorType.GET, managerId);
        }
    }


    /**
     * Find all the blocks in a given test.
     *
     * @param testId - the given test id
     * @return a list of all of the blocks in the test.
     */
    public List<BlockWrapper> getTestBlocksForTest(long testId) throws DBException {
        try {
            List<TestBlock> preWrapped = dao.getTestBlocks(testId);
            List<BlockWrapper> blocks = new ArrayList<BlockWrapper>();
            for (TestBlock block : preWrapped) {
                blocks.add(new BlockWrapper(blockDAO.getAllBlockQuestions(block.getId()), block));
            }
            logger.info("Successfully found all test blocks for a test. testID: " + testId);
            return blocks;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all test blocks for a test. testID: " + testId, e);
            throw new DBException(ErrorType.GET, testId);
        }
    }

    /**
     * Find all cognitive test questions in a test.
     *
     * @param testId - The test Id.
     * @return - All questions that the test has.
     */
    public List<TestQuestion> getTestQuestionsForTest(long testId) throws DBException {
        try {
            List<TestQuestion> toReturn = dao.getTestQuestions(testId);
            logger.info("Successfully found all test questions for a test. testID: " + testId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all test questions for a test. testID: " + testId, e);
            throw new DBException(ErrorType.GET, testId);
        }
    }


    /**
     * Method for getting all tests with a specific description in notes field.
     *
     * @param notes - The notes files filter.
     * @return - All tests that their notes field contains the notes string parameter.
     */
    public List<CognitiveTest> filterTestsByNotes(String notes) throws DBException {
        try {
            List<CognitiveTest> toReturn = dao.filterTestsByNotes(notes);
            logger.info("Successfully found all tests for a given notes. notes: " + notes);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all tests for given notes. notes: " + notes, e);
            throw new DBException(ErrorType.GET, null);
        }
    }


    /**
     * Method for getting all tests of a specific project.
     *
     * @param projectFilter - The project test filter.
     * @return - All tests that their project field is the same as the projectFilter parameter.
     */
    public List<CognitiveTest> filterTestsByProject(String projectFilter) throws DBException {
        try {
            List<CognitiveTest> toReturn = dao.filterTestsByProject(projectFilter);
            logger.info("Successfully found all tests for a given project. Project: " + projectFilter);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all tests for a given project. Project: " + projectFilter, e);
            throw new DBException(ErrorType.GET, null);
        }
    }


    /**
     * Method for searching for all cognitive tests of a manager without fetching the questions.
     *
     * @param managerId - id of the manager the request is build on.
     * @return - All tests that their manager has the id (param) without the questions (no wrapper)
     */
    public List<CognitiveTest> findTestsForTestManagerWithoutQuestions(long managerId) throws DBException {
        try {
            List<CognitiveTest> toReturn = dao.getCognitiveTestOfManager(managerId);
            logger.info("Successfully found all tests for a given manager, without questions. ManagerID: " + managerId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all tests for a given manager, without questions. ManagerID: " + managerId, e);
            throw new DBException(ErrorType.GET, managerId);
        }
    }

}