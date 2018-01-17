package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.entities.*;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class})
@SpringBootTest
public class TestSubjectServiceTest {

    @Autowired
    private TestBlockDAOimpl bdao;

    @Autowired
    private TestSubjectDAOimpl sdao;

    @Autowired
    private TestQuestionDAOimpl qdao;

    @Autowired
    private CognitiveTestDAOimpl tdao;

    @Autowired
    private TestManagerDAOimpl mdao;

    @Autowired
    private TestAnswerDAOimpl adao;

    @Autowired
    private TestSubjectDAOimpl dao;


    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(sdao);
        Mockito.reset(bdao);
        Mockito.reset(adao);
        Mockito.reset(tdao);
        Mockito.reset(qdao);
        Mockito.reset(mdao);

    }
    /*
    For the sake of mocking:
    subject Id - 1
    manager Id - 6
    question id - 8
    test Id - 9
     */

    @Test
    public void FullTest(){
        TestSubjectService service = new TestSubjectService(dao);
        CognitiveTestService testService = new CognitiveTestService(tdao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);
        TestAnswerService answerService = new TestAnswerService(adao,sdao);
        QuestionService questionService = new QuestionService(qdao,adao,tdao,mdao);
        TestBlockService blockService = new TestBlockService(bdao);


        TestSubject subject = service.createTestSubject("Simha Gora",45645,"Queue");
        assertNotNull("Problem with creating a test subject",subject);

        doReturn(subject).when(dao).get(Long.valueOf(1));
        TestSubject result = service.findTestSubject(1);

        assertEquals("Problem with getting a test subject",result,subject);

        subject.setBrowser("Tor");
        service.updateTestSubject(subject);

        result = service.findTestSubject(1);

        assertEquals("Problem with updating a test subject", "Tor",result.getBrowser());

        TestManager manager = managerService.createTestManager("Ein li Rayon Leod Shem","Maeshu kal");
        CognitiveTest test = testService.createTestForTestManager("test",manager, 1, 0);
        TestBlock block = blockService.createTestBlock(2,false,"teag", test);
        TestQuestion question = new TestQuestion("When will the Shibutzim arrive?",1,99999,"Questions that remain unasnwered",
                block, test, manager);
        questionService.createTestQuestion(question);

        TestSubject subject1 = service.createTestSubject("Timon",789,"Hakuna");
        TestSubject subject2 = service.createTestSubject("Pumba",654,"Matata");
        TestSubject subject3 = service.createTestSubject("Simba",12,"Safari");

        TestAnswer answer = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer);
        TestAnswer answer1 = new TestAnswer(subject1,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject2,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject3,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer3);

        List<TestSubject> subjects = new ArrayList<TestSubject>();
        subjects.add(subject);
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);

        doReturn(subjects).when(dao).getTestSubjectsWhoParticipatedInTest(9);
        List<TestSubject> testSubjectList = service.findTestSubjectsWhoParticipatedInTest(9);
        for (TestSubject t : testSubjectList){
            assertTrue("Getting unrelated tests while trying to get all subjects from a specific test",subjects.contains(t));
        }
        for (TestSubject t : subjects){
            assertTrue("Didn't get all the subjects from a specific test",testSubjectList.contains(t));
        }

        TestAnswer answer4 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer6);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);

        doReturn(answers).when(dao).getSubjectAnswers(8);
        List<TestAnswer> answerList = service.findAllTestSubjectAnswers(8);
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answer while trying to get all answers of a specific subject",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers from a specific subject",answerList.contains(t));
        }


        service.deleteTestSubject(1);
        service.deleteTestSubject(2);
        service.deleteTestSubject(3);
        service.deleteTestSubject(4);


        managerService.deleteTestManager(5);

        testService.deleteTestForTestManager(6);

        blockService.deleteTestBlock(7);

        questionService.deleteTestQuestion(8);

        answerService.deleteAllTestAnswersForQuestion(9);
        answerService.deleteAllTestAnswersForQuestion(10);
        answerService.deleteAllTestAnswersForQuestion(11);
        answerService.deleteAllTestAnswersForQuestion(12);
        answerService.deleteAllTestAnswersForQuestion(13);
        answerService.deleteAllTestAnswersForQuestion(14);
        answerService.deleteAllTestAnswersForQuestion(15);


    }


}