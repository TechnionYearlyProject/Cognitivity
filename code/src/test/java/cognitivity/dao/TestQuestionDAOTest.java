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
    private TestQuestionDAO testQuestionDAO;
    private TestQuestion testQuestion;
    private TestManager testManager;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testQuestionDAO
     *  - testQuestion
     *
     */
    //TODO: add check in the add questions test, that the test indeed updates
    @Before
    public void initialize(){
        testQuestionDAO = new TestQuestionDAO();
        testManager =
                new TestManager("onlyForTests TestManager", "notarealpassword");
        CognitiveTest cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 1, 0);
        TestBlock testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
        testQuestion = new TestQuestion("testQuestion", 0, null, "testTag", testBlock, cognitiveTest);
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add testQuestion to the db
     *      we check if we succeed by trying to fetch the testQuestion by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     *
     */
    @Test
    void crudTests(){
        assertNull(testQuestionDAO.get(0L));
        testQuestionDAO.add(testQuestion);
        assertNotNull("add testQuestion problem", testQuestionDAO.get(testQuestion.getId()));
        String questionTag = testQuestion.getTag();
        assertTrue("Tag incorrect",
                questionTag.equals(testQuestionDAO.get(testQuestion.getId()).getTag()));
        String newQuestionTag = "bla bla bli";
        testQuestion.setTag(newQuestionTag);
        testQuestionDAO.update(testQuestion);
        assertTrue("tag update incorrect",
                newQuestionTag.equals(testQuestionDAO.get(testQuestion.getId()).getTag()));
        testQuestion.setTag(questionTag);
        testQuestionDAO.update(testQuestion);
        assertTrue("Tag incorrect",
                questionTag.equals(testQuestionDAO.get(testQuestion.getId()).getTag()));
        testQuestionDAO.delete(testQuestion.getId());
        assertNull("delete problem", testQuestionDAO.get(testQuestion.getId()));
    }



    /*
     * This test will check getTestQuestionsFromAManager functionality:
     *
     *  - we check that the test manager have no questions when the question table is
     *     empty (before we created any)
     *  - we check that the test manager have no questions when the question table isn't empty
     *    (but the test manager don't have any questions yet)
     *  - we check that the test manager have questions when we update one question to be its question
     *  - we remove the question from the DB and validating that the manager don't have any questions
     *
     */
    @Test
    void getTestQuestionsFromAManager(){
        assertNull("testQuestion table should be empty",
                testQuestionDAO.getTestQuestionsFromAManager(testManager));
        testQuestionDAO.add(testQuestion);
        assertNull("Test manager shouldn't have any questions yet",
                testQuestionDAO.getTestQuestionsFromAManager(testManager));
        testQuestion.setTestManager(testManager);
        testQuestionDAO.update(testQuestion);
        assertTrue("Test manager should have this question",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).contains(testQuestion));
        assertTrue("Test manager should have only one question",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).size() == 1);
        testQuestionDAO.delete(testQuestion.getId());
        assertNull("Test manager shouldn't have any questions yet",
                testQuestionDAO.getTestQuestionsFromAManager(testManager));
    }
}
