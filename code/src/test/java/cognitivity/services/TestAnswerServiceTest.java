package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.entities.*;
import cognitivity.web.app.config.HibernateBeanConfiguration;
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
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class},
        locations = {"classpath:testApplicationContext.xml", "classpath:test-dispatcher-servlet.xml"})
@SpringBootTest
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
    public void FullTest() {
        QuestionService questionService = new QuestionService(qdao,dao,tdao,mdao);
        TestBlockService blockService = new TestBlockService(bdao);
        CognitiveTestService testService = new CognitiveTestService(tdao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);
        TestSubjectService subjectService = new TestSubjectService(sdao);
        TestAnswerService service = new TestAnswerService(dao,sdao);

        TestManager manager = new TestManager("Bohen Ben Mivhan", "Paswordd");
        CognitiveTest test = new CognitiveTest("Sifratiyot", manager, 2, 1);
        CognitiveTest test2 = new CognitiveTest("jhfkasjhfkajdfak", manager, 2, 1);
        TestBlock block = blockService.createTestBlock(1, false, "tagiity tag", test);
        TestSubject subject = new TestSubject("Rick", 12321, "Ahla dafdefan");
        TestQuestion question = new TestQuestion("Who the f&$# builds a stonehenge?", 4, 5,
                "Questions we will never answer", block, test, manager);
        questionService.createTestQuestion(question);
        TestQuestion question1 = new TestQuestion("Who ate my sandwich?", 4, 5,
                "Questions we will never answer", block, test2, manager);
        questionService.createTestQuestion(question1);

        TestAnswer answer = new TestAnswer(subject, question, test, 2, 1, 1, 2,
                "None!", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer);

        assertNotNull("Problem with creating a test answer", answer);

        doReturn(answer).when(dao).get(Long.valueOf(1));
        TestAnswer result = service.findTestAnswerById(1);

        assertEquals("Problem with getting an answer by Id", result, answer);

        answer.setNumberOfClick(5);
        service.updateTestAnswerForQuestion(answer);

        result = service.findTestAnswerById(1);
        int numericResult = result.getNumberOfClick();
        assertEquals("Problem with updating a question", 5, numericResult);


        TestAnswer answer1 = new TestAnswer(subject, question, test, 2, 1, 1, 2,
                "Yodel", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject, question, test, 2, 1, 1, 2,
                "Operah", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject, question, test, 2, 1, 1, 2,
                "Windows", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer3);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer3);
        answers.add(answer2);
        answers.add(answer1);

        doReturn(answers).when(dao).getTestAnswers(1);
        List<TestAnswer> answerList = service.findAllTestAnswerForAQuestion(1);
        for (TestAnswer t : answerList) {
            assertTrue("Getting unrelated answers while trying to get all answers for a specific question", answers.contains(t));
        }
        for (TestAnswer t : answers) {
            assertTrue("Didn't get all the answers for a specific question", answerList.contains(t));
        }

        TestAnswer answer4 = new TestAnswer(subject, question1, test2, 2, 1, 1, 2,
                "Snape killed Dumbeldore", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject, question1, test2, 2, 1, 1, 2,
                "Bruce willis is dead on the 6th sense", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject, question1, test2, 2, 1, 1, 2,
                "Brad Pit and Edward Norton are the same guy on Fight club", false, 43, false, false, true);
        service.addTestAnswerForTestQuestion(answer6);


        doReturn(answers).when(dao).getTestSubjectAnswersInTest(2,3);
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

        testService.deleteTestForTestManager(7);
        testService.deleteTestForTestManager(8);

        blockService.deleteTestBlock(9);

        subjectService.deleteTestSubject(2);
    }


}