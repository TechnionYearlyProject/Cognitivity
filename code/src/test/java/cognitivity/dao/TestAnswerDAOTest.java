package cognitivity.dao;

import cognitivity.entities.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@Ignore("need to debug")
public class TestAnswerDAOTest {
    TestAnswerDAO testAnswerDAO;
    CognitiveTest cognitiveTest;
    TestManager testManager;
    TestQuestion testQuestion;
    TestBlock testBlock;
    TestSubject testSubject;
    TestAnswer testAnswer;

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
    public void initialize(){
        testAnswerDAO = new TestAnswerDAO();
        CognitiveTestDAO cognitiveTestDAO = new CognitiveTestDAO();
        testManager =
                new TestManager("onlyForTests TestManager", "notarealpassword");
        cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 1, 0);
        testBlock = new TestBlock(0,false, "testTag", cognitiveTest);
        testQuestion = new TestQuestion("testQuestion", 0, null, "testTag", testBlock, cognitiveTest);
        cognitiveTestDAO.add(cognitiveTest);
        testSubject = new TestSubject("testName",-1, "fireFox");
        testAnswer = new TestAnswer(testSubject, testQuestion,
                cognitiveTest, 0, 0, 0,
                0, "blabla", false,
                "notime", false, false, false);
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add the answer to the db
     *      we check if we succeed by trying to fetch the answer by id
     *  - Read : we call the get function with feu parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer steel in the db
     */
    @Test
    void crudTests(){
        assertNull(testAnswerDAO.get(0L));
        testAnswerDAO.add(testAnswer);
        assertNotNull("add testAnswer problem", testAnswerDAO.get(testAnswer.getId()));
        int finalAnswer = testAnswer.getFinalAnswer();
        assertTrue("finalAnswer incorrect",
                finalAnswer == testAnswerDAO.get(testAnswer.getId()).getFinalAnswer());
        int newFinalAnswer = 2;
        testAnswer.setFinalAnswer(newFinalAnswer);
        testAnswerDAO.update(testAnswer);
        assertTrue("finalAnswer update incorrect",
                newFinalAnswer == testAnswerDAO.get(testAnswer.getId()).getFinalAnswer());
        testAnswer.setFinalAnswer(finalAnswer);
        assertTrue("finalAnswer incorrect",
                finalAnswer == testAnswerDAO.get(testAnswer.getId()).getFinalAnswer());
        testAnswerDAO.update(testAnswer);
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
    void getTestSubjectAnswersInTest(){
        List<TestAnswer> answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject, cognitiveTest);
        assertTrue("problem with getting testAnswers for subject", answers.isEmpty());
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject, cognitiveTest);
        assertTrue("problem with getting testAnswers for subject", answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 1);

        TestSubject newTestSubject = new TestSubject("anotherName",-1, "fireFox");
        testAnswer.setTestSubject(newTestSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        assertTrue("problem with adding questions of another subject", !answers.contains(testAnswer));
        assertTrue("problem with adding questions of another subject", answers.size() == 1);

        testAnswer.setTestSubject(testSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject, cognitiveTest);
        assertTrue("problem with adding answers testAnswers for subject", answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 2);

        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject, cognitiveTest);
        assertTrue("problem with adding answers testAnswers for subject", !answers.contains(testAnswer));
        assertTrue("problem with getting testAnswers for subject", answers.size() == 1);
        testAnswerDAO.delete(testAnswer.getId() - 1);
        testAnswerDAO.delete(testAnswer.getId() - 2);
        testAnswer.setId(testAnswer.getId() - 2);

        answers = testAnswerDAO.getTestSubjectAnswersInTest(testSubject, cognitiveTest);
        assertTrue("problem with getting testAnswers for subject", answers.isEmpty());
    }

    /*
     * checks the getTestAnswers function:
     *     first, we check that there isn't an answers for the test
     *     next, we check that the list updates itself when we add an answer
     *     next, we check that the list updates when we add an answer of another subject
     *     next, we check that when we remove an answer the list returns without this answer
     *     at the end we remove all the question and check that the list is empty
     *
     */
    @Test
    void getTestAnswers(){
        List<TestAnswer> answers = testAnswerDAO.getTestAnswers(testQuestion);
        assertTrue(answers.isEmpty());

        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestAnswers(testQuestion);
        assertTrue(answers.contains(testAnswer));
        assertTrue(answers.size() == 1);

        TestSubject newTestSubject = new TestSubject("anotherName",-1, "fireFox");
        testAnswer.setTestSubject(newTestSubject);
        testAnswer.setId(testAnswer.getId() + 1);
        testAnswerDAO.add(testAnswer);
        answers = testAnswerDAO.getTestAnswers(testQuestion);
        assertTrue(answers.contains(testAnswer));
        assertTrue(answers.size() == 2);

        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getTestAnswers(testQuestion);
        assertTrue(!answers.contains(testAnswer));
        assertTrue(answers.size() == 1);

        testAnswer.setTestSubject(testSubject);
        testAnswer.setId(testAnswer.getId() - 1);
        testAnswerDAO.delete(testAnswer.getId());
        answers = testAnswerDAO.getTestAnswers(testQuestion);
        assertTrue(answers.isEmpty());
    }

}
