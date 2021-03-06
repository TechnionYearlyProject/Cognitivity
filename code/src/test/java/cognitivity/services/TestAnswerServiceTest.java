package cognitivity.services;

/**
 * A test class for Test answer service
 *
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class TestAnswerServiceTest {

    @Autowired
    private TestAnswerDAO dao;

    @Autowired
    private TestBlockDAO bdao;

    @Autowired
    private TestQuestionDAO qdao;

    @Autowired
    private CognitiveTestDAO tdao;

    @Autowired
    private TestManagerDAO mdao;

    @Autowired
    private TestAnswerDAO adao;

    @Autowired
    private TestSubjectDAO sdao;


    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(bdao);
        Mockito.reset(sdao);
        Mockito.reset(adao);
        Mockito.reset(tdao);
        Mockito.reset(qdao);
        Mockito.reset(mdao);

    }

    /**
     * Testing all functionalities of TestAnswerService
     */
    /*
    For the sake of mocking:
    answerd id - 1
    subject - 2
    test - 3
     */
    @Test
    public void FullTest() throws Exception {
        QuestionService questionService = new QuestionService(qdao, dao, tdao, mdao);
        TestBlockService blockService = new TestBlockService(bdao);
        CognitiveTestService testService = new CognitiveTestService(tdao, bdao, qdao);
        TestManagerService managerService = new TestManagerService(mdao, tdao);
        TestSubjectService subjectService = new TestSubjectService(sdao);
        TestAnswerService service = new TestAnswerService(dao, sdao);

        TestManager manager = new TestManager("mail");
        CognitiveTest test = new CognitiveTest("Sifratiyot", manager, 1, "notes", "project");
        test.setId(3L);
        CognitiveTest test2 = new CognitiveTest("jhfkasjhfkajdfak", manager, 1, "notes", "project");
        BlockWrapper block = new BlockWrapper(1, false, "tagiity tag", test);
        block.setId(4L);
        TestSubject subject = new TestSubject("Rick", "ip", "Ahla dafdefan", "2013", "Mad scientist", "a widow :(", "email");
        TestQuestion question = new TestQuestion("Who the f&$# builds a stonehenge?", "Stam link", block.innerBlock(test.getId()),
                test, manager);
        questionService.createTestQuestion(question);
        TestQuestion question1 = new TestQuestion("Who ate my sandwich?", "Stam link", block.innerBlock(test.getId()), test2,
                manager);
        questionService.createTestQuestion(question1);

        TestAnswer answer = new TestAnswer(subject, question, test, "None!");
        service.addTestAnswerForTestQuestion(answer);

        assertNotNull("Problem with creating a test answer", answer);

        doReturn(answer).when(dao).get(Long.valueOf(1));
        TestAnswer result = service.findTestAnswerById(1);

        assertEquals("Problem with getting an answer by Id", result, answer);

        answer.setFinalAnswer("5");
        service.updateTestAnswerForQuestion(answer);

        result = service.findTestAnswerById(1);
        String res = result.getFinalAnswer();
        assertTrue("Problem with updating a question", "5".equals(res));


        TestAnswer answer1 = new TestAnswer(subject, question, test, "Yodel");
        service.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject, question, test, "Operah");
        service.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject, question, test, "Windows");
        service.addTestAnswerForTestQuestion(answer3);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer3);
        answers.add(answer2);
        answers.add(answer1);

        doReturn(answers).when(dao).getQuestionAnswers(1);
        List<TestAnswer> answerList = service.findAllTestAnswerForAQuestion(1);
        for (TestAnswer t : answerList) {
            assertTrue("Getting unrelated answers while trying to get all answers for a specific question", answers.contains(t));
        }
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the answers for a specific question", answerList.contains(t));
        }

        TestAnswer answer4 = new TestAnswer(subject, question1, test2, "Snape killed Dumbeldore");
        service.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject, question1, test2, "Bruce willis is dead on the 6th sense");
        service.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject, question1, test2,
                "Brad Pit and Edward Norton are the same guy on Fight club");
        service.addTestAnswerForTestQuestion(answer6);


        doReturn(subject).when(sdao).get(2L);
        doReturn(answers).when(dao).getTestSubjectAnswersInTest(2, 3);
        answerList = service.findTestAnswersBySubjectInTest(2, 3);
        for (TestAnswer t : answerList) {
            assertTrue("Getting unrelated answers while trying to get all answers for a specific test", answers.contains(t));
        }
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the answers for a specific test", answerList.contains(t));
        }
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);

        doReturn(answers).when(sdao).getSubjectAnswers(2);
        answerList = service.findTestAnswersBySubject(2);
        for (TestAnswer t : answerList) {
            assertTrue("Getting unrelated answers while trying to get all answers from a certain subject", answers.contains(t));
        }
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the answers for a specific subject", answerList.contains(t));
        }

        service.deleteTestAnswerForQuestion(1);

        service.deleteAllTestAnswersForQuestion(1);
        service.deleteAllTestAnswersForQuestion(4);
        service.deleteAllTestAnswersForQuestion(5);

        managerService.deleteTestManager(6);


        blockService.deleteTestBlock(9);

        subjectService.deleteTestSubject(2);


        doReturn(answers).when(dao).getTestAnswers(7L);
        answerList = service.findAllTestAnswersForATest(7L);
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the answers for a specific test", answerList.contains(t));
        }
        for (TestAnswer t : answerList) {
            assertTrue("Getting unrelated answers while trying to get all answers from a certain test", answers.contains(t));
        }


        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try {
            service.addTestAnswerForTestQuestion(new TestAnswer());
            assertTrue("Problem with handling with exception at create", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updateTestAnswerForQuestion(new TestAnswer());
            assertTrue("Problem with handling with exception at update", false);
        } catch (Exception e) {
        }
        doThrow(new org.hibernate.HibernateException("")).when(dao).getQuestionAnswers(7);
        try {
            service.deleteAllTestAnswersForQuestion(7);
            assertTrue("Problem with handling with exception at delete", false);
        } catch (Exception e) {
        }
        doThrow(new org.hibernate.HibernateException("")).when(dao).delete(any());
        try {
            service.deleteTestAnswerForQuestion(7);
            assertTrue("Problem with handling with exception at delete", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(dao).get(7L);
        try {
            service.findTestAnswerById(7);
            assertTrue("Problem with handling with exception at findTestAnswerById", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(sdao).getSubjectAnswers(7L);
        try {
            service.findTestAnswersBySubject(7);
            assertTrue("Problem with handling with exception at findTestAnswersBySubject", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(dao).getTestSubjectAnswersInTest(7L, 6L);
        try {
            service.findTestAnswersBySubjectInTest(7, 6);
            assertTrue("Problem with handling with exception at findTestAnswersBySubjectInTest", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(dao).getQuestionAnswers(7L);
        try {
            service.findAllTestAnswerForAQuestion(7);
            assertTrue("Problem with handling with exception at findAllTestAnswerForAQuestion", false);
        } catch (Exception e) {
        }

        doThrow(new org.hibernate.HibernateException("")).when(dao).getTestAnswers(7L);
        try {
            service.findAllTestAnswersForATest(7L);
            assertTrue("Problem with handling with exception at findAllTestAnswersForATest", false);
        } catch (Exception e) {
        }

    }


}