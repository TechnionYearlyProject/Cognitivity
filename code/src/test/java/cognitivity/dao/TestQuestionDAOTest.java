package cognitivity.dao;


import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateBeanConfiguration.class,
        locations = {"classpath:testApplicationContext.xml", "classpath:test-dispatcher-servlet.xml"})
@Ignore("tests passing, but to run them there is a need of db")
public class TestQuestionDAOTest extends AbstractDaoTestClass {

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
        testManager =
                new TestManager("onlyForTests TestManager", "notarealpassword");
        testManagerDAO.add(testManager);
        CognitiveTest cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 1, 0);
        cognitiveTestDAO.add(cognitiveTest);
        TestBlock testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
        testBlockDAO.add(testBlock);
        testQuestion = new TestQuestion("testQuestion", 0,
                null, "testTag", testBlock, cognitiveTest, testManager);
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
    public void crudTests(){
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
    public void getTestQuestionsFromAManager(){
        assertTrue("testQuestion table should be empty",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).isEmpty());
        TestManager otherTestManager
                = new TestManager("new TestManager", "notarealpassword");
        testManagerDAO.add(otherTestManager);
        testQuestion.setTestManager(otherTestManager);
        testQuestionDAO.add(testQuestion);
        assertTrue("Test manager should have one test",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).isEmpty());
        testQuestion.setTestManager(testManager);
        testQuestionDAO.update(testQuestion);
        assertTrue("Test manager should have this question",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).contains(testQuestion));
        assertTrue("Test manager should have only one question",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).size() == 1);
        testQuestionDAO.delete(testQuestion.getId());
        assertTrue("Test manager shouldn't have any questions yet",
                testQuestionDAO.getTestQuestionsFromAManager(testManager).isEmpty());
    }
}
