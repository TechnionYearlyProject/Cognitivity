package cognitivity.dao;

import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.web.app.config.CognitivityMvcConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CognitivityMvcConfiguration.class})
@Ignore("tests passing, but to run them there is a need of db")
public class TestBlockDAOTest extends AbstractDaoTestClass {

    private TestBlock testBlock;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testBlockDAO
     *  - testBlock
     *
     */
    @Before
    public void initialize(){
        TestManager testManager =
                new TestManager("onlyForTests TestManager");
        testManagerDAO.add(testManager);
        CognitiveTest cognitiveTest = new CognitiveTest("onlyForTests", testManager, 0, 1);
        cognitiveTestDAO.add(new TestWrapper(cognitiveTest));
        testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add block to the db
     *      we check if we succeed by trying to fetch the block by id
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
    }

    @Test
    public void getAllBlockQuestions(){
        //TODO!
    }
}
