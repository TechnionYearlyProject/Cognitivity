package cognitivity.dao;

import cognitivity.entities.*;
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
public class TestAnswerDAOTest extends AbstractDaoTestClass {

    private CognitiveTest cognitiveTest;
    private TestManager testManager;
    private TestQuestion testQuestion;
    private TestBlock testBlock;
    private TestSubject testSubject;
    private TestAnswer testAnswer;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testAnswerDAO
     *  - testManager
     *  - cognitiveTest :
     *      the state is just an arbitrary number choice
     *  - testBlock :
     *      the testBlock will hold the question that we will create answers for her in the tests
     *  - testQuestion :
     *      the question that we will hold answers for, the block that will contains its
     *      answer will be testBlock that we created
     *  - testSubject :
     *      the subject that will fill the test.
     *  - testAnswer :
     *      the answer for the test(finally), it will hold all the previous objects we created
     *
     */
    @Before
    public void initialize() {
        testManager =
                new TestManager("onlyForTests TestManager notarealpassword");
        testManagerDAO.add(testManager);
        cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 0, "notes", "project");
        cognitiveTestDAO.add(cognitiveTest);
        testBlock = new TestBlock(0, false, "testTag", cognitiveTest);
        testBlockDAO.add(testBlock);
        testQuestion = new TestQuestion("testQuestion","Stam link", testBlock, cognitiveTest, testManager);
        testQuestionDAO.add(testQuestion);
        testSubject = new TestSubject("testName", "-1", "fireFox", "DD/MM/YYYY", "a test holder", "Will he? Won't he?", "email");
        testSubjectDAO.add(testSubject);
        testAnswer = new TestAnswer(testSubject, testQuestion,
                cognitiveTest, "blabla");
    }

    @After
    public void clean(){
        testManagerDAO.delete(testManager.getId());
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add the answer to the db
     *      we check if we succeed by trying to fetch the answer by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     */
    @Test
    public void crudTests() {
        assertNull(testAnswerDAO.get(0L));
        testAnswerDAO.add(testAnswer);
        assertNotNull("add testAnswer problem", testAnswerDAO.get(testAnswer.getId()));
        String finalAnswer = testAnswer.getFinalAnswer();
        assertTrue("finalAnswer incorrect",
                finalAnswer.equals(testAnswerDAO.get(testAnswer.getId()).getFinalAnswer()));
        String newFinalAnswer = "2";
        testAnswer.setFinalAnswer(newFinalAnswer);
        testAnswerDAO.update(testAnswer);
        assertTrue("finalAnswer update incorrect",
                newFinalAnswer.equals(testAnswerDAO.get(testAnswer.getId()).getFinalAnswer()));
        testAnswer.setFinalAnswer(finalAnswer);
        testAnswerDAO.update(testAnswer);
        assertTrue("finalAnswer incorrect",
                finalAnswer.equals(testAnswerDAO.get(testAnswer.getId()).getFinalAnswer()));
        testAnswerDAO.delete(testAnswer.getId());
        assertNull("delete problem", testAnswerDAO.get(testAnswer.getId()));
    }

    /*
     * checks the getTestSubjectAnswersInTest function:
     *     first, we check that there isn't an answers for the question ( by the subject)
     *     next, we check that the list updates itself when we add an answer
     *     next, we check that the list don't change when we add an answer of another subject
     *     next, we check that the list updates itself when we add an answer
     *     next, we check that when we remove an answer the list returns without this answer
     *     at the end we remove all the question and check that the list is empty
     *
     */
    @Test
    public void getTestSubjectAnswersInTest() {
        List<TestAnswer> answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with getting testAnswers for subject", answers.isEmpty());
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with getting testAnswers for subject", answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 1);

        TestSubject newTestSubject = new TestSubject("anotherName", "-1", "fireFox", "-3", "ocup up", "Donated in the office", "email");
        testSubjectDAO.add(newTestSubject);
        testAnswer.setTestSubject(newTestSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with adding questions of another subject", !answers.contains(testAnswer));
        assertTrue("problem with adding questions of another subject", answers.size() == 1);

        testAnswer.setTestSubject(testSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with adding answers testAnswers for subject", answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 2);

        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with adding answers testAnswers for subject", !answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 1);
        testAnswerDAO.delete(testAnswer.getId() - 1);
        testAnswerDAO.delete(testAnswer.getId() - 2);
        testAnswer.setId(testAnswer.getId() - 2);

        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject.getId(), cognitiveTest.getId());
        assertTrue("problem with getting testAnswers for subject", answers.isEmpty());
    }

    /*
     * checks the getQuestionAnswers function:
     *     first, we check that there isn't an answers for the question
     *     next, we check that the list updates itself when we add an answer
     *     next, we check that the list updates when we add an answer of another subject
     *     next, we check that when we remove an answer the list returns without this answer
     *     at the end we remove all the question and check that the list is empty
     *
     */
    @Test
    public void getQuestionAnswers() {
        List<TestAnswer> answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(answers.isEmpty());

        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(answers.contains(testAnswer));
        assertTrue(answers.size() == 1);

        TestSubject newTestSubject = new TestSubject("anotherName", "-1", "fireFox", "2 minutes before that other guy", "A place holder", "Nope!", "email");
        testSubjectDAO.add(newTestSubject);
        testAnswer.setTestSubject(newTestSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(answers.contains(testAnswer));
        assertTrue(answers.size() == 2);

        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(!answers.contains(testAnswer));
        assertTrue(answers.size() == 1);

        testAnswer.setTestSubject(testSubject);
        testAnswer.setId(testAnswer.getId() - 1);
        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(answers.isEmpty());
    }


    /*
     * checks the getTestAnswers function:
     *     first, we check that there isn't an answers for the test
     *     next, we check that the list updates itself when we add an answer
     *     next, we check that the list updates when we add an answer of another subject
     *     next, we check that the list updates when we add an answer of another question
     *     
     */
    @Test
    public void getTestAnswers(){
        List<TestAnswer> answers = testAnswerDAO.getQuestionAnswers(testQuestion.getId());
        assertTrue(answers.isEmpty());

        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestAnswers(cognitiveTest.getId());
        assertTrue(answers.size() == 1);

        TestSubject newTestSubject = new TestSubject("anotherName", "-1", "fireFox", "aahh", "ooh", "eehh", "email");
        testSubjectDAO.add(newTestSubject);
        testAnswer.setTestSubject(newTestSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestAnswers(cognitiveTest.getId());
        assertTrue(answers.size() == 2);

        testQuestion.setId(testQuestion.getId() + 1);
        testQuestionDAO.add(testQuestion);

        testAnswer.setId(testAnswer.getId() + 1);
        testAnswer.setQuestion(testQuestion);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestAnswers(cognitiveTest.getId());
        assertTrue(answers.size() == 3);

    }
}
