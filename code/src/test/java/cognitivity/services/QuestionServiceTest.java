package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.*;
import cognitivity.exceptions.DBException;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import config.TestContextBeanConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class QuestionServiceTest {

    @Autowired
    private TestQuestionDAO testQuestionDAO;

    @Autowired
    private CognitiveTestDAO cognitiveTestDAO;

    @Autowired
    private TestManagerDAO testManagerDAO;

    @Autowired
    private TestAnswerDAO answerDao;

    @Autowired
    private TestSubjectDAO testSubjectDAO;


    @Autowired
    private TestBlockDAO testBlockDAO;

    @Before
    public void setup() {

        Mockito.reset(testQuestionDAO);
        Mockito.reset(testSubjectDAO);
        Mockito.reset(answerDao);
        Mockito.reset(cognitiveTestDAO);
        Mockito.reset(testBlockDAO);
        Mockito.reset(testManagerDAO);

    }

    /*
    For the sake of mocking:
    question id - 1
    test id - 2
    manager id - 3
     */

    /**
     * Testing all functionalities Question service
     */
    @Test
    public void fullTest()throws Exception {
        QuestionService service = new QuestionService(testQuestionDAO, answerDao, cognitiveTestDAO, testManagerDAO);
        TestBlockService blockService = new TestBlockService(testBlockDAO);
        CognitiveTestService testService = new CognitiveTestService(cognitiveTestDAO,testBlockDAO, testQuestionDAO);
        TestManagerService managerService = new TestManagerService(testManagerDAO, cognitiveTestDAO);

        TestManager manager = new TestManager("lkljl");
        CognitiveTest cognitiveTest = new CognitiveTest("test1", manager, 1, 100, "notes", "project");
        TestWrapper testWrapper = new TestWrapper(cognitiveTest);
        TestWrapper test = new TestWrapper();
        try {
            test = testService.createTestForTestManager(testWrapper);
        }catch (DBException e ){

        }
        BlockWrapper block = new BlockWrapper(5, true, "Taggy tag", cognitiveTest);


        TestQuestion start = new TestQuestion("What is the meaning of life?", 1,"None, it's meaningless", "Unanswered questions", block.innerBlock(1), cognitiveTest, manager, 0);
        TestQuestion question = service.createTestQuestion(start);

        assertNotNull("Problem with creating a test question", question);

        doReturn(question).when(testQuestionDAO).get(1L);
        TestQuestion result = service.findQuestionById(1);

        assertEquals("Problem with getting a test question by ID", question, result);

        question.setAnswer("Now there is!");

        service.updateTestQuestion(question);

        result = service.findQuestionById(1);
        String numericAnswer = result.getAnswer();

        assertEquals("Problem with updating the question", "Now there is!", numericAnswer);

        BlockWrapper block2 = blockService.createTestBlock(2, true, "Togos", cognitiveTest);

        TestQuestion question1 = new TestQuestion("Who moved my cheese?", 5, "HE!", "Critical for life", block.innerBlock(11), cognitiveTest, manager, 0);
        service.createTestQuestion(question1);
        TestQuestion question2 = new TestQuestion("Who framed Roger Rabbit?", 1, "The monorail!", "Movie questions", block2.innerBlock(1), cognitiveTest, manager, 0);
        service.createTestQuestion(question2);
        TestQuestion question3 = new TestQuestion("Question! Question?", 1, "Answer? Answer!", "What?! Who?!", block2.innerBlock(1), cognitiveTest, manager, 0);
        service.createTestQuestion(question3);

        List<TestQuestion> questions = new ArrayList<>();
        questions.add(question);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        doReturn(questions).when(cognitiveTestDAO).getTestQuestions(2);
        List<TestQuestion> questions1 = service.findAllTestQuestionsFromTestId(2);
        for (TestQuestion t : questions1) {
            assertTrue("Getting unrelated results while trying to get all test questions", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions from the test", questions1.contains(t));
        }

        CognitiveTest cognitiveTest1 = new CognitiveTest("test1", manager, 1, 100, "notes", "project");
        TestWrapper testWrapper1 = new TestWrapper(cognitiveTest1);
        TestWrapper test2 = new TestWrapper();
        try {
            test2 = testService.createTestForTestManager(testWrapper1);
        }catch (DBException e){

        }

        TestQuestion question4 = new TestQuestion("Who moved my cheese?", 5, "The cat", "Critical for life", block.innerBlock(1), cognitiveTest1, manager, 0);
        service.createTestQuestion(question4);
        TestQuestion question5 = new TestQuestion("Who framed Roger Rabbit?", 1, "rail", "Movie questions", block2.innerBlock(1), cognitiveTest1, manager, 0);
        service.createTestQuestion(question5);
        TestQuestion question6 = new TestQuestion("Question! Question?", 1, "!", "What?! Who?!", block2.innerBlock(1), cognitiveTest1, manager, 0);
        service.createTestQuestion(question6);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);

        doReturn(questions).when(testQuestionDAO).getTestQuestionsFromAManager(any());
        questions1 = service.findAllTestQuestionsFromManagerId(3);
        for (TestQuestion t : questions1) {
            assertTrue("Getting unrelated results while trying to get all test questions from all tests", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions from all the tests", questions1.contains(t));
        }

        TestAnswerService answerService = new TestAnswerService(answerDao, testSubjectDAO);
        TestSubjectService subjectService = new TestSubjectService(testSubjectDAO);
        TestSubject testSubject = new TestSubject("Timothy k miller", "Pip", "Safchrome");
        TestSubject subject = subjectService.createTestSubject(testSubject);

        TestAnswer answer = new TestAnswer(subject, question, cognitiveTest, 52, 43, 2, 3, "Bla is bla", true, 52,
                false, false, true);
        answerService.addTestAnswerForTestQuestion(answer);
        TestAnswer answer1 = new TestAnswer(subject, question, cognitiveTest, 52, 43, 2, 3, "Bla is bla", true, 52,
                false, false, true);
        answerService.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject, question, cognitiveTest, 52, 43, 2, 3, "Bla is bla", true, 52,
                false, false, true);
        answerService.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject, question, cognitiveTest, 52, 43, 2, 3, "Bla is bla", true, 52,
                false, false, true);
        answerService.addTestAnswerForTestQuestion(answer3);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        doReturn(answers).when(answerDao).getTestAnswers(1);
        List<TestAnswer> res = service.getTestAnswers(1);
        for (TestAnswer t : res) {
            assertTrue("Getting unrelated results while trying to get all the answers to the question", answers.contains(t));
        }
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the question answers", res.contains(t));
        }

        service.deleteTestQuestion(1);
        service.deleteTestQuestion(4);
        service.deleteTestQuestion(5);
        service.deleteTestQuestion(6);
        service.deleteTestQuestion(7);
        service.deleteTestQuestion(8);
        service.deleteTestQuestion(9);

        managerService.deleteTestManager(2);

        blockService.deleteTestBlock(3);

        doThrow(new org.hibernate.HibernateException("")).when(testQuestionDAO).add(any());
        try{
            service.createTestQuestion(new TestQuestion());
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(testQuestionDAO).update(any());
        try {
            service.updateTestQuestion(new TestQuestion());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(testQuestionDAO).delete(any());
        try {
            service.deleteTestQuestion(7);
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}
    }
}