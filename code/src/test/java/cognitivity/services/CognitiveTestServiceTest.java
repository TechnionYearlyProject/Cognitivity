package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class},
        locations = {"classpath:spring/test-context.xml", "classpath:spring/test-dispatcher-servlet.xml"})
@SpringBootTest
public class CognitiveTestServiceTest {


    @Autowired
    private CognitiveTestDAO dao;

    @Autowired
    private TestManagerDAO mdao;

    @Autowired
    private TestQuestionDAO qdao;

    @Autowired
    private TestAnswerDAO adao;

    @Autowired
    private TestBlockDAO bdao;



    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(mdao);
        Mockito.reset(adao);
        Mockito.reset(qdao);
        Mockito.reset(bdao);

        doNothing().when(dao).add(any());
        doNothing().when(qdao).add(any());
        doNothing().when(bdao).add(any());
        doNothing().when(dao).delete(any());
        doNothing().when(qdao).delete(any());
        doNothing().when(bdao).delete(any());



    }

    //TODO: check if we need to delete the created parameters from the DB at the end of the test.
    @Test
    /*
    For the sake of mocking:
    test id = 7
    manager id = 9

     */
    public void FullTest() {


        TestManager manager = new TestManager();

        CognitiveTestService service = new CognitiveTestService(dao);

        CognitiveTest cognitiveTest = new CognitiveTest("test1", manager, 1, 2);
        CognitiveTest test = service.createTestForTestManager(cognitiveTest);

        doReturn(test).when(dao).get(Long.valueOf(7));
        assertNotNull("Problem in making test", test);

        CognitiveTest test1 = new CognitiveTest("Man's not hot", manager, 2, 2);
        CognitiveTest test2 = new CognitiveTest("Two plus two is", manager, 4, 6);
        CognitiveTest test3 = new CognitiveTest("Minus 0ne that's", manager, 3, 10);
        CognitiveTest test4 = new CognitiveTest("Quick maths!", manager, 3, 17);

        TestBlockService blockService = new TestBlockService(bdao);
        TestBlock block1 = blockService.createTestBlock(2, true, "tag1", test);
        TestBlock block2 = blockService.createTestBlock(5, false, "tag2", test);
        TestBlock block3 = blockService.createTestBlock(4, true, "tag3", test);
        TestBlock block4 = blockService.createTestBlock(8, false, "tag4", test);


        CognitiveTest getting = service.findTestById(7);
        assertNotNull("Problem in finding the test", getting);
        test.setName("Skum toom toom toom");
        service.updateTestForTestManager(test);
        getting = service.findTestById(7);
        assertEquals("Problem with updating", getting.getName(), test.getName());
        List<CognitiveTest> tests = new ArrayList<CognitiveTest>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        doReturn(tests).when(dao).getCognitiveTestOfManager(9);
        List<CognitiveTest> result = service.findTestsForTestManager(9);
        for (CognitiveTest t : result) {
            assertTrue("Getting unrelated results while trying to get all managers tests", tests.contains(t));
        }
        for (CognitiveTest t : tests) {
            assertTrue("Didn't get all the tests from the manager", result.contains(t));
        }


        List<TestBlock> blocks = new ArrayList<TestBlock>();
        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);
        blocks.add(block4);

        doReturn(blocks).when(dao).getTestBlocks(7);
        List<TestBlock> blockResult = service.getTestBlocksForTest(7);
        for (TestBlock t : blockResult) {
            assertTrue("Getting unrelated results while trying to get all test blocks", blocks.contains(t));
        }
        for (TestBlock t : blocks) {
            assertTrue("Didn't get all the blocks from the test", blockResult.contains(t));
        }

        QuestionService questionService = new QuestionService(qdao,adao,dao,mdao);

        TestQuestion question1 = new TestQuestion("question1", 1, "5", "bla", block1, test, manager, 0);
        questionService.createTestQuestion(question1);
        TestQuestion question2 = new TestQuestion("question2", 1, "5", "bla2", block2, test, manager, 0);
        questionService.createTestQuestion(question2);
        TestQuestion question3 = new TestQuestion("question3", 1, "5", "bla3", block3, test, manager, 0);
        questionService.createTestQuestion(question3);

        List<TestQuestion> questions = new ArrayList<TestQuestion>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        doReturn(questions).when(dao).getTestQuestions(7);
        List<TestQuestion> questionRes = service.getTestQuestionsForTest(7);
        for (TestQuestion t : questionRes) {
            assertTrue("Getting unrelated question while trying to get all test questions", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions from the test", questionRes.contains(t));
        }


        service.deleteTestForTestManager(7);
        service.deleteTestForTestManager(11);
        service.deleteTestForTestManager(12);
        service.deleteTestForTestManager(13);
        service.deleteTestForTestManager(14);

        blockService.deleteTestBlock(15);
        blockService.deleteTestBlock(16);
        blockService.deleteTestBlock(17);
        blockService.deleteTestBlock(18);

        questionService.deleteTestQuestion(19);
        questionService.deleteTestQuestion(20);
        questionService.deleteTestQuestion(21);


    }


}