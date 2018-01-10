package cognitivity.dao;


import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore("need to debug")
public class TestQuestionDAOTest {
    TestQuestionDAO testQuestionDAO;
    TestQuestion testQuestion;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testQuestionDAO
     *  - testQuestion
     *
     */
    @Before
    public void initialize(){
        testQuestionDAO = new TestQuestionDAO();
        TestManager testManager =
                new TestManager("onlyForTests TestManager", "notarealpassword");
        CognitiveTest cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 1, 0);
        TestBlock testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
        testQuestion = new TestQuestion("testQuestion", 0, null, "testTag", testBlock, cognitiveTest);

    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add testManager to the db
     *      we check if we succeed by trying to fetch the manager by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     */
    @Test
    void crudTests(){
        assertNull(testQuestionDAO.get(0L));
        testQuestionDAO.add(testQuestion);
        assertNotNull("add testQuestion problem", testQuestionDAO.get(testQuestion.getId()));
        String questionTag = testQuestion.getTag();
        assertTrue("Tag incorrect",
                questionTag == testQuestionDAO.get(testQuestion.getId()).getTag());
        String newQuestionTag = "bla bla bli";
        testQuestion.setTag(newQuestionTag);
        testQuestionDAO.update(testQuestion);
        assertTrue("tag update incorrect",
                newQuestionTag == testQuestionDAO.get(testQuestion.getId()).getTag());
        testQuestion.setTag(questionTag);
        testQuestionDAO.update(testQuestion);
        assertTrue("Tag incorrect",
                questionTag == testQuestionDAO.get(testQuestion.getId()).getTag());
        testQuestionDAO.delete(testQuestion.getId());
        assertNull("delete problem", testQuestionDAO.get(testQuestion.getId()));
    }
    
    @Test
    void getTestQuestionsFromAManager(){

    }
}
