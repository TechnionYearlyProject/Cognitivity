package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import config.IntegrationTestContextConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Guy on 20/1/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
@Ignore("tests passing, but to run them there is a need of db")
public class TestBlockDAOTest extends AbstractDaoTestClass {

    private TestBlock testBlock;
    private CognitiveTest cognitiveTest;
    private TestManager testManager;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testBlockDAO
     *  - testBlock
     *
     */
    @Before
    public void initialize(){
        testManager =
                new TestManager("onlyForTests TestManager");
        testManagerDAO.add(testManager);
        cognitiveTest = new CognitiveTest("onlyForTests", testManager, 1, "notes", "project");
        cognitiveTestDAO.add(cognitiveTest);
        testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
    }

    @After
    public void clean(){
        testManagerDAO.delete(testManager.getId());
    }


    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add block to the db
     *      we check if we succeed by trying to fetch the block by id
     *      we also check the add function with foreign key and not full object
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     */
    @Test
    public void crudTests(){
        assertNull(testBlockDAO.get(0L));
        testBlockDAO.add(testBlock);
        assertNotNull("add testBlock problem", testBlockDAO.get(testBlock.getId()));
        int numberOfQuestions = testBlock.getNumberOfQuestions();
        assertTrue("numberOfQuestions incorrect",
                numberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        int newNumberOfQuestions = 2;
        testBlock.setNumberOfQuestions(newNumberOfQuestions);
        testBlockDAO.update(testBlock);
        assertTrue("testBlock update incorrect",
                newNumberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        testBlock.setNumberOfQuestions(numberOfQuestions);
        testBlockDAO.update(testBlock);
        assertTrue("finalAnswer incorrect",
                numberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        testBlockDAO.delete(testBlock.getId());
        assertNull("delete problem", testBlockDAO.get(testBlock.getId()));

        testBlock.setCognitiveTest(null);
        testBlockDAO.add(testBlock, cognitiveTest.getId());
        TestBlock returnedTestBlock = testBlockDAO.get(testBlock.getId());
        String message = "add testBlock with foreign keys problem";
        assertNotNull(message, returnedTestBlock);
        assertEquals(message, returnedTestBlock.getCognitiveTest().getManager().getEmail(),
                cognitiveTest.getManager().getEmail());
    }

    @Test
    public void getAllBlockQuestions(){
        testBlockDAO.add(testBlock);
        List<TestQuestion> questionsList = testBlockDAO.getAllBlockQuestions(testBlock.getId());
        assertTrue("block should be empty", questionsList.isEmpty());
        int numberOfQuestions = 1000;
        TestQuestion questions[] = new TestQuestion[numberOfQuestions];
        for(int i = 0; i < numberOfQuestions; i++){
            questions[i] = new TestQuestion("question " + i,"Stam link" , testBlock,
                    cognitiveTest, testManager);
            testQuestionDAO.add(questions[i]);
            questionsList = testBlockDAO.getAllBlockQuestions(testBlock.getId());
            assertTrue("block should be empty", questionsList.contains(questions[i]));
            assertTrue("block should be empty", questionsList.size() == i + 1);
        }
        testBlockDAO.delete(testBlock.getId());
        questionsList = testBlockDAO.getAllBlockQuestions(testBlock.getId());
        assertTrue("block should be empty", questionsList.isEmpty());
    }
}
