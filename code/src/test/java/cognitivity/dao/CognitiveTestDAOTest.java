package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import cognitivity.web.app.config.CognitivityMvcConfiguration;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import config.TestContextBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CognitivityMvcConfiguration.class})
@Ignore("tests passing, but to run them there is a need of db")
public class CognitiveTestDAOTest extends AbstractDaoTestClass {

    private TestManager[] testManagers;
    private CognitiveTest[][] cognitiveTestsPerManager;
    final private int numOfTestManagers = 5;
    final private int numOfTestsPerManager = 5;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - cognitiveTestDAO
     *  - testManager
     *  - cognitiveTest :
     *      the state is just an arbitrary number choice
     *
     */
    @Before
    public void initialize() {
        testManagers = new TestManager[numOfTestManagers];
        //new TestManager("onlyForTests TestManager", "notarealpassword");
        //testManagerDAO.add(testManager);
        cognitiveTestsPerManager = new CognitiveTest[numOfTestManagers][numOfTestsPerManager];
        //new CognitiveTest("onlyForTests", testManager, 1, 0);
        for (int i = 0; i < numOfTestManagers; i++) {
            testManagers[i] = new TestManager("TestManager" + i + " @gmail.com");
            testManagerDAO.add(testManagers[i]);
            for (int j = 0; j < numOfTestsPerManager; j++) {
                cognitiveTestsPerManager[i][j] = new CognitiveTest("Test" + i + j,
                        testManagers[i], 1, 0);
            }
        }

    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add cognitiveTest to the db
     *      we check if we succeed by trying to fetch the answer by id
     *  - Read : we call the get function with feu parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     *
     *  we do this test in loop only because we have a lot of tests and maybe something
     *  unexpected might happen
     *
     */
    @Test
    public void crudTests() {
        assertNull(cognitiveTestDAO.get(0L));
        for (int i = 0; i < numOfTestManagers; i++) {
            for (int j = 0; j < numOfTestsPerManager; j++) {
                CognitiveTest cognitiveTest = cognitiveTestsPerManager[i][j];
                cognitiveTestDAO.add(cognitiveTest);
                assertNotNull("add cognitiveTest problem",
                        cognitiveTestDAO.get(cognitiveTest.getId()));
                int state = cognitiveTest.getState();
                assertTrue("state incorrect",
                        state == cognitiveTestDAO.get(cognitiveTest.getId()).getState());
                int newState = 2;
                cognitiveTest.setState(newState);
                cognitiveTestDAO.update(cognitiveTest);
                assertTrue("state update incorrect",
                        newState == cognitiveTestDAO.get(cognitiveTest.getId()).getState());
                cognitiveTest.setState(state);
                cognitiveTestDAO.update(cognitiveTest);
                assertTrue("state update incorrect",
                        state == cognitiveTestDAO.get(cognitiveTest.getId()).getState());

            }
        }

        for (int i = 0; i < numOfTestManagers; i++) {
            for (int j = 0; j < numOfTestsPerManager; j++) {
                cognitiveTestDAO.delete(cognitiveTestsPerManager[i][j].getId());
                assertNull("delete problem",
                        cognitiveTestDAO.get(cognitiveTestsPerManager[i][j].getId()));

            }
        }
    }


    /*
     * checks the getCognitiveTestOfManager function:
     *
     *     - we check that there isn't any tests for the manager
     *     - we check that the list updated when we add an test
     *     - we check that the list don't change when we add an test from another manager
     *     - at the end the testManager test and check that the list is empty
     *     - at the end we remove all the tests
     *
     */
    @Test
    public void getCognitiveTestOfManager() {
        List<CognitiveTest> cognitiveTestList =
                cognitiveTestDAO.getCognitiveTestOfManager(testManagers[0].getId());
        assertTrue("should return empty list", cognitiveTestList.isEmpty());

        cognitiveTestDAO.add(cognitiveTestsPerManager[0][0]);
        cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManagers[0].getId());
        assertTrue("the cognitive test should be on the list",
                cognitiveTestList.contains(cognitiveTestsPerManager[0][0]));
        assertTrue("the cognitive test should be the only test on the list",
                cognitiveTestList.size() == 1);
        cognitiveTestDAO.delete(cognitiveTestsPerManager[0][0].getId());


        for (int i = 0; i < numOfTestsPerManager; i++) {
            for (int j = 0; j < numOfTestManagers; j++) {
                cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManagers[j].getId());
                assertTrue("testManager number " + j + " should had " + i +
                        " tests in his list at this point", cognitiveTestList.size() == i);
                cognitiveTestDAO.add(cognitiveTestsPerManager[j][i]);
                cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManagers[j].getId());
                assertTrue("testManager number " + j + " should had " + (i + 1) +
                        " tests in his list at this point", cognitiveTestList.size() == i + 1);
            }
        }

        for (int i = numOfTestsPerManager; i >= 0; i--) {
            for (int j = 0; j < numOfTestManagers; j++) {
                cognitiveTestList =
                        cognitiveTestDAO.getCognitiveTestOfManager(testManagers[j].getId());
                assertTrue("delete problem",
                        cognitiveTestList.size() == i);
                if (i > 0)
                    cognitiveTestDAO.delete(cognitiveTestsPerManager[j][i - 1].getId());

            }
        }
    }


    /*
     * checks the getTestQuestions function:
     *
     *     add questions and validate that the list updates
     *
     */
    @Test
    public void getTestQuestions() {
        int numOfTestQuestionsPerBlock = 10;
        int numOfBlocks = numOfTestsPerManager;
        // we will put the results in questions
        List<TestQuestion> questions;
        TestBlock[] testBlocks = new TestBlock[numOfBlocks];
        TestQuestion[][] testQuestions =
                new TestQuestion[numOfBlocks][numOfTestQuestionsPerBlock];
        // adding the tests to the db
        for(int i = 0; i < numOfTestsPerManager; i++){
            cognitiveTestDAO.add(cognitiveTestsPerManager[0][i]);
        }

        for (int i = 0; i < numOfBlocks; i++) {
            testBlocks[i] = new TestBlock(numOfTestQuestionsPerBlock, (i % 2) == 1,
                    "tag : blocknum" + i, cognitiveTestsPerManager[0][i]);
            testBlockDAO.add(testBlocks[i]);
            for (int j = 0; j <= numOfTestQuestionsPerBlock; j++) {
                questions = cognitiveTestDAO.
                        getTestQuestions(cognitiveTestsPerManager[0][i].getId());
                assertTrue("num of questions don't fit the number of the expected number",
                        questions.size() == j);
                //as long as we arn't in the last iteration
                if (j < numOfTestQuestionsPerBlock) {
                    testQuestions[i][j] =
                            new TestQuestion("to be or not to be", 5, "10", "tag",
                                    testBlocks[i], cognitiveTestsPerManager[0][i], testManagers[0], 0);
                    testQuestionDAO.add(testQuestions[i][j]);
                }
            }
        }

        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < numOfTestQuestionsPerBlock; j++) {
                testQuestionDAO.delete(testQuestions[i][j].getId());
            }
            testBlockDAO.delete(testBlocks[i].getId());
            // each CognitiveTest have one block, and therefore when we finish erasing all the question
            // from the block the list should be empty
            questions = cognitiveTestDAO.
                    getTestQuestions(cognitiveTestsPerManager[0][i].getId());
            assertTrue("num of questions don't fit the number of the expected number",
                    questions.isEmpty());
        }

        // removing the tests from the db
        for(int i = 0; i < numOfTestsPerManager; i++){
            cognitiveTestDAO.add(cognitiveTestsPerManager[0][i]);
        }
    }

    /*
     * checks the getTestBlocks function:
     *
     *     - we cheating blocks, each iteration for other test
     *     - at each creation of test we check that the list of blocks is in the expected size
     *     - we also check that in the new added block is in the list
     *     - at the end we remove all the blocks
     *
     */
    @Test
    public void getTestBlocks() {

        // adding the tests to the db
        for(int i = 0; i < numOfTestsPerManager; i++){
            cognitiveTestDAO.add(cognitiveTestsPerManager[0][i]);
        }

        int numOfBlocks = 10;
        TestBlock[][] testBlocks = new TestBlock[numOfTestsPerManager][numOfBlocks];
        List<TestBlock> blocks;
        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < numOfTestsPerManager; j++) {
                blocks = cognitiveTestDAO.getTestBlocks(cognitiveTestsPerManager[0][j].getId());
                assertTrue("numOfBlocks different from expected",
                        blocks.size() == i);
                testBlocks[j][i] = new TestBlock(0, (i % 2) == 1,
                        "tag : blocknum" + i, cognitiveTestsPerManager[0][j]);
                testBlockDAO.add(testBlocks[j][i]);
                blocks = cognitiveTestDAO.getTestBlocks(cognitiveTestsPerManager[0][j].getId());
                assertTrue("this block should have been in the list", blocks.contains(testBlocks[j][i]));
            }

        }

        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < numOfTestsPerManager; j++) {
                testBlockDAO.delete(testBlocks[j][i].getId());
            }
        }

        // removing the tests from the db
        for(int i = 0; i < numOfTestsPerManager; i++){
            cognitiveTestDAO.add(cognitiveTestsPerManager[0][i]);
        }
    }

}
