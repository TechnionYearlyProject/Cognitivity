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

import static org.junit.Assert.*;
/**
 * Created by Guy on 20/1/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
@Ignore("tests passing, but to run them there is a need of db")
public class TestQuestionDAOTest extends AbstractDaoTestClass {

    private TestQuestion testQuestion;
    private TestManager testManager;
    private TestBlock testBlock;
    private CognitiveTest cognitiveTest;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testQuestionDAO
     *  - testQuestion
     *
     */
    @Before
    public void initialize(){
        testManager =
                new TestManager("onlyForTests TestManagernotarealpassword");
        testManagerDAO.add(testManager);
        cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 0, "notes", "project");
        cognitiveTestDAO.add(cognitiveTest);
        testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
        testBlockDAO.add(testBlock);
        testQuestion = new TestQuestion("testQuestion","Stam link", testBlock, cognitiveTest, testManager);
    }

    @After
    public void clean(){
        testManagerDAO.delete(testManager.getId());
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add testQuestion to the db
     *      we check if we succeed by trying to fetch the testQuestion by id
     *      we also check the add function with foreign keys and not full objects
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
        String question = testQuestion.getQuestion();
        assertTrue("QuestionCoverTest string incorrect",
                question.equals(testQuestionDAO.get(testQuestion.getId()).getQuestion()));
        String newQuestion = "bla bla bli";
        testQuestion.setQuestion(newQuestion);
        testQuestionDAO.update(testQuestion);
        assertTrue("QuestionCoverTest string update incorrect",
                newQuestion.equals(testQuestionDAO.get(testQuestion.getId()).getQuestion()));
        testQuestion.setQuestion(question);
        testQuestionDAO.update(testQuestion);
        assertTrue("QuestionCoverTest string incorrect",
                question.equals(testQuestionDAO.get(testQuestion.getId()).getQuestion()));
        testQuestionDAO.delete(testQuestion.getId());
        assertNull("delete problem", testQuestionDAO.get(testQuestion.getId()));

        testQuestion.setTestManager(null);
        testQuestion.setTestBlock(null);
        testQuestion.setCognitiveTest(null);
        testQuestionDAO.add(testQuestion,  testBlock.getId(),
                cognitiveTest.getId(), testManager.getId());
        String errorMessage = "add testQuestion problem";
        TestQuestion returnedTestQuestion = testQuestionDAO.get(testQuestion.getId());
        assertNotNull(errorMessage, returnedTestQuestion);
        assertEquals(errorMessage, returnedTestQuestion.getTestManager().getEmail(),
                testManager.getEmail());
        assertEquals(errorMessage, returnedTestQuestion.getTestBlock().getId(),
                testBlock.getId());
        assertEquals(errorMessage, returnedTestQuestion.getCognitiveTest().getId(),
                cognitiveTest.getId());
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
                = new TestManager("new TestManager notarealpassword");
        testManagerDAO.add(otherTestManager);
        testQuestion.setTestManager(otherTestManager);
        testQuestionDAO.add(testQuestion);
        assertTrue("Test manager shouldn't have testsQuestions",
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
        testManagerDAO.delete(otherTestManager.getId());
    }

    /*
     * This test will check findPictureLinkPerQuestion functionality:
     *
     *  - we check that the picture link of the image identical to the link we saved
     *
     */
    public void findPictureLinkPerQuestion(){
        String picLink = testQuestionDAO.findPictureLinkPerQuestion(testQuestion.getId());
        assertTrue("Wrong picture link", picLink.equals(testQuestion.getPictureLink()));
    }
}
