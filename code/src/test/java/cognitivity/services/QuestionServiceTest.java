package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.entities.*;
import cognitivity.services.*;
import config.TestContextBeanConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class})
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private TestQuestionDAOimpl dao;

    @Autowired
    private CognitiveTestDAOimpl tdao;

    @Autowired
    private TestManagerDAOimpl mdao;

    @Autowired
    private TestAnswerDAOimpl adao;

    @Autowired
    private TestSubjectDAOimpl sdao;


    @Autowired
    private TestBlockDAOimpl bdao;

    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(sdao);
        Mockito.reset(adao);
        Mockito.reset(tdao);
        Mockito.reset(bdao);
        Mockito.reset(mdao);

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
    public void fullTest(){
        QuestionService service = new QuestionService(dao,adao,tdao,mdao);
        TestBlockService blockService = new TestBlockService(bdao);
        CognitiveTestService testService = new CognitiveTestService(tdao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);

        TestManager manager = managerService.createTestManager("The bossy boss boss","Admin Admin");
        CognitiveTest test = testService.createTestForTestManager("teee",manager, 1, 100);
        TestBlock block = blockService.createTestBlock(5,true,"Taggy tag",test);


        TestQuestion start = new TestQuestion("What is the meaning of life?", 1, 42, "Unanswered questions",block, test,manager);
        TestQuestion question = service.createTestQuestion(start);

        assertNotNull("Problem with creating a test question", question);

        doReturn(question).when(dao).get(Long.valueOf(1));
        TestQuestion result = service.findQuestionById(1);

        assertEquals("Problem with getting a test question by ID", question,result);

        question.setAnswer(43);

        service.updateTestQuestion(question);

        result = service.findQuestionById(1);
        int numericAnswer  = result.getAnswer();

        assertEquals("Problem with updating the question", 43, numericAnswer);


        TestBlock block2 = blockService.createTestBlock(2,true,"Togos",test);

        TestQuestion question1 = new TestQuestion("Who moved my cheese?", 5, 52, "Critical for life",block, test,manager);
        service.createTestQuestion(question1);
        TestQuestion question2 = new TestQuestion("Who framed Roger Rabbit?", 1, 54, "Movie questions",block2, test,manager);
        service.createTestQuestion(question2);
        TestQuestion question3 = new TestQuestion("Question! Question?", 1, 42, "What?! Who?!",block2, test,manager);
        service.createTestQuestion(question3);

        List<TestQuestion> questions = new ArrayList<>();
        questions.add(question);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        doReturn(questions).when(tdao).getTestQuestions(2);
        List<TestQuestion> questions1 = service.findAllTestQuestionsFromTestId(2);
        for (TestQuestion t : questions1){
            assertTrue("Getting unrelated results while trying to get all test questions",questions.contains(t));
        }
        for (TestQuestion t : questions){
            assertTrue("Didn't get all the questions from the test",questions1.contains(t));
        }

        CognitiveTest test2 = testService.createTestForTestManager("Cognitivity2. Coming to the nearest theater",manager, 1, 100);

        TestQuestion question4 = new TestQuestion("Who moved my cheese?", 5, 52, "Critical for life",block, test2,manager);
        service.createTestQuestion(question4);
        TestQuestion question5 = new TestQuestion("Who framed Roger Rabbit?", 1, 54, "Movie questions",block2, test2,manager);
        service.createTestQuestion(question5);
        TestQuestion question6 = new TestQuestion("Question! Question?", 1, 42, "What?! Who?!",block2, test2,manager);
        service.createTestQuestion(question6);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);

        doReturn(questions).when(dao).getTestQuestionsFromAManager(any());
        questions1 = service.findAllTestQuestionsFromManagerId(3);
        for (TestQuestion t : questions1){
            assertTrue("Getting unrelated results while trying to get all test questions from all tests",questions.contains(t));
        }
        for (TestQuestion t : questions){
            assertTrue("Didn't get all the questions from all the tests",questions1.contains(t));
        }

        TestAnswerService answerService = new TestAnswerService(adao,sdao);
        TestSubjectService subjectService = new TestSubjectService(sdao);
        TestSubject subject = subjectService.createTestSubject("Timothy k miller",111,"Safchrome");

        TestAnswer answer = new TestAnswer(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        answerService.addTestAnswerForTestQuestion(answer);
        TestAnswer answer1 = new TestAnswer(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        answerService.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        answerService.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        answerService.addTestAnswerForTestQuestion(answer3);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        doReturn(answers).when(adao).getTestAnswers(1);
        List<TestAnswer> res = service.getTestAnswers(1);
        for (TestAnswer t : res){
            assertTrue("Getting unrelated results while trying to get all the answers to the question",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the question answers",res.contains(t));
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

        testService.deleteTestForTestManager(10);

    }




}