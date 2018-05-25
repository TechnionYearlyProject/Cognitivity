package cognitivity.services;
/**
 * A test class for Test subject service
 * @Author - Pe'er
 * @Date - 2.2.18
 */
import cognitivity.dao.*;
import cognitivity.dto.BlockWrapper;
import cognitivity.entities.*;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class TestSubjectServiceTest {

    @Autowired
    private TestBlockDAO bdao;

    @Autowired
    private TestSubjectDAO sdao;

    @Autowired
    private TestQuestionDAO qdao;

    @Autowired
    private CognitiveTestDAO tdao;

    @Autowired
    private TestManagerDAO mdao;

    @Autowired
    private TestAnswerDAO adao;

    @Autowired
    private TestSubjectDAO dao;


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
    public void FullTest()throws Exception{
        TestSubjectService service = new TestSubjectService(dao);
        CognitiveTestService testService = new CognitiveTestService(tdao,bdao, qdao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);
        TestAnswerService answerService = new TestAnswerService(adao,sdao);
        QuestionService questionService = new QuestionService(qdao,adao,tdao,mdao);
        TestBlockService blockService = new TestBlockService(bdao);

        TestSubject testSubject = new TestSubject("Simha Gora", "pipipp", "Queue","some","thing","here");
        TestSubject subject = new TestSubject();
        try {
            subject = service.createTestSubject(testSubject);
        }catch (Exception e){
            assertTrue("Problem with adding the test",false);
        }
        assertNotNull("Problem with creating a test subject",subject);

        doReturn(subject).when(dao).get(1L);
        TestSubject result = service.findTestSubject(1);

        assertEquals("Problem with getting a test subject",result,subject);

        subject.setBrowser("Tor");
        try {
            service.updateTestSubject(subject);
        }catch (Exception e){
            assertTrue("problem with updating subject",false);
        }

        result = service.findTestSubject(1);

        assertEquals("Problem with updating a test subject", "Tor",result.getBrowser());

        TestManager manager = new TestManager("Yo!!!!!!!!!!!!1");
        CognitiveTest test = new CognitiveTest("test", manager, 0, "notes", "project");
        test.setId(9L);
        BlockWrapper block = new BlockWrapper();
        try {
            block = blockService.createTestBlock(2,false,"teag", test);
        }catch (Exception e){
            assertTrue("problem with updating subject",false);
        }
        TestQuestion question = new TestQuestion("When will the Shibutzim arrive?","Stam link", block.innerBlock(test.getId()),
                test, manager);
        questionService.createTestQuestion(question);

        TestSubject subject1 = service.createTestSubject(new TestSubject("Timon", "Cow", "Hakuna","1987","A guardian","Same sex married"));
        TestSubject subject2 = service.createTestSubject(new TestSubject("Pumba", "pig", "Matata","1987","A guardian","Same sex married"));
        TestSubject subject3 = service.createTestSubject(new TestSubject("Simba", "Lion", "Safari","1987","A king!","Married? at least I think so?"));
        TestSubject subject4 = service.createTestSubject(new TestSubject("Mufasa", "Lion", "Safari","1987","A dead king!","Married to the ground (in which he ie burried)"));
        TestSubject subject5 = service.createTestSubject(new TestSubject("Scar", "Lion", "Safari","1987","A false king!","Married! to EVIL!"));


        doReturn(8L).when(dao).add(any());
        service.createTestSubject(subject1);
        service.createTestSubject(subject2);
        service.createTestSubject(subject3);
        service.createTestSubject(subject4);
        service.createTestSubject(subject5);

        TestAnswer answer = new TestAnswer(subject,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer);
        TestAnswer answer1 = new TestAnswer(subject1,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject2,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject3,question, test, "To have no worries");
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

        TestAnswer answer4 = new TestAnswer(subject,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject,question, test, "To have no worries");
        answerService.addTestAnswerForTestQuestion(answer6);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);

        doReturn(subject).when(dao).get(8L);
        doReturn(answers).when(dao).getSubjectAnswers(8);
        List<TestAnswer> answerList = service.findAllTestSubjectAnswers(8);
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answer while trying to get all answers of a specific subject",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers from a specific subject",answerList.contains(t));
        }

        List<TestSubject> all = new ArrayList<>();
        all.add(testSubject);
        all.add(subject1);
        all.add(subject2);
        all.add(subject3);
        all.add(subject4);
        all.add(subject5);

        doReturn(all).when(dao).findAllTestSubjectsInTheSystem();
        List<TestSubject> allRes = service.findAllTestSubjectsInTheSystem();
        for (TestSubject t : all){
            assertTrue("Getting unrelated answer while trying to get all answers of a specific subject",allRes.contains(t));
        }
        for (TestSubject t : allRes){
            assertTrue("Didn't get all the answers from a specific subject",all.contains(t));
        }


        try {
            service.deleteTestSubject(1);
            service.deleteTestSubject(2);
            service.deleteTestSubject(3);
            service.deleteTestSubject(4);


            managerService.deleteTestManager(5);


            blockService.deleteTestBlock(7);

            questionService.deleteTestQuestion(8);

            answerService.deleteAllTestAnswersForQuestion(9);
            answerService.deleteAllTestAnswersForQuestion(10);
            answerService.deleteAllTestAnswersForQuestion(11);
            answerService.deleteAllTestAnswersForQuestion(12);
            answerService.deleteAllTestAnswersForQuestion(13);
            answerService.deleteAllTestAnswersForQuestion(14);
            answerService.deleteAllTestAnswersForQuestion(15);
        }catch (Exception e){
            assertTrue("Problem with deleting the tests",false);
        }


        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try{
            service.createTestSubject(new TestSubject());
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updateTestSubject(new TestSubject());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).delete(89L);
        try {
            service.deleteTestSubject(89L);
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).get(89L);
        try {
            service.findTestSubject(89L);
            assertTrue("Problem with handling with exception at findTestSubject",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).getSubjectAnswers(89L);
        try {
            service.findAllTestSubjectAnswers(89L);
            assertTrue("Problem with handling with exception at findAllTestSubjectAnswers",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).getTestSubjectsWhoParticipatedInTest(89L);
        try {
            service.findTestSubjectsWhoParticipatedInTest(89L);
            assertTrue("Problem with handling with exception at findTestSubjectsWhoParticipatedInTest",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).findAllTestSubjectsInTheSystem();
        try {
            service.findAllTestSubjectsInTheSystem();
            assertTrue("Problem with handling with exception at findAllTestSubjectsInTheSystem",false);
        }catch (Exception e){}
    }


}