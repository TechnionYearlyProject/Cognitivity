package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
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
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
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

        doReturn(7L).when(dao).add(any());
        doReturn(7L).when(qdao).add(any());
        doReturn(7L).when(bdao).add(any());
        doNothing().when(dao).delete(any());
        doNothing().when(qdao).delete(any());
        doNothing().when(bdao).delete(any());
        doNothing().when(mdao).delete(any());
        doNothing().when(adao).delete(any());


    }

    @Test
    /*
    For the sake of mocking:
    test id = 7
    manager id = 9

     */
    public void FullTest() throws Exception{


        TestManager manager = new TestManager();
        manager.setId(9L);

        CognitiveTestService service = new CognitiveTestService(dao, bdao, qdao);

        CognitiveTest cognitiveTest = new CognitiveTest("test1", manager, 2, "notes", "project");
        cognitiveTest.setId(7L);
        TestWrapper testWrapper = new TestWrapper(cognitiveTest);
        TestWrapper test = new TestWrapper();
        try {
            test = service.createTestForTestManager(testWrapper);
        }catch (DBException e){

        }

        doReturn(test.innerTest()).when(dao).get(7L);
        assertNotNull("Problem in making test", cognitiveTest);

        CognitiveTest test1 = new CognitiveTest("Man's not hot", manager, 2, "notes", "project");
        test1.setId(8L);
        CognitiveTest test2 = new CognitiveTest("Two plus two is", manager, 6, "notes", "project");
        test2.setId(9L);
        CognitiveTest test3 = new CognitiveTest("Minus 0ne that's", manager, 10, "notes", "project");
        test3.setId(10L);
        CognitiveTest test4 = new CognitiveTest("Quick maths!", manager, 17, "notes", "project");
        test4.setId(11L);

        TestBlockService blockService = new TestBlockService(bdao);
        BlockWrapper block1 = blockService.createTestBlock(2, true, "tag1", cognitiveTest);
        block1.setId(12L);
        BlockWrapper block2 = blockService.createTestBlock(5, false, "tag2", cognitiveTest);
        block2.setId(13L);
        BlockWrapper block3 = blockService.createTestBlock(4, true, "tag3", cognitiveTest);
        block3.setId(14L);
        BlockWrapper block4 = blockService.createTestBlock(8, false, "tag4", cognitiveTest);
        block4.setId(15L);


        TestWrapper getting = service.findTestById(7);
        assertNotNull("Problem in finding the test", getting);
        test.setName("Skum toom toom toom");
        service.updateTestForTestManager(new TestWrapper(cognitiveTest));
        doReturn(test.innerTest()).when(dao).get(7L);
        getting = service.findTestById(7);
        assertEquals("Problem with updating", getting.getName(), test.getName());
        List<TestWrapper> tests = new ArrayList<TestWrapper>();
        tests.add(new TestWrapper(cognitiveTest));
        tests.add(new TestWrapper(test1));
        tests.add(new TestWrapper(test2));
        tests.add(new TestWrapper(test3));
        tests.add(new TestWrapper(test4));

        List<CognitiveTest> preWrapped = new ArrayList<>();
        preWrapped.add(cognitiveTest);
        preWrapped.add(test1);
        preWrapped.add(test2);
        preWrapped.add(test3);
        preWrapped.add(test4);

        doReturn(preWrapped).when(dao).getCognitiveTestOfManager(9);

        doReturn(cognitiveTest).when(dao).get(7L);
        doReturn(test1).when(dao).get(8L);
        doReturn(test2).when(dao).get(9L);
        doReturn(test3).when(dao).get(10L);
        doReturn(test4).when(dao).get(11L);
        List<TestWrapper> result = new ArrayList<>();
        try {
            result = service.findTestsForTestManager(9);
        }catch (DBException e){

        }
        for (TestWrapper t : result) {
            assertTrue("Getting unrelated results while trying to get all managers tests", tests.contains(t));
        }
        for (TestWrapper t : tests) {
            assertTrue("Didn't get all the tests from the manager", result.contains(t));
        }


        List<TestBlock> blocks = new ArrayList<TestBlock>();
        blocks.add(block1.innerBlock(test.getId()));
        blocks.add(block2.innerBlock(test.getId()));
        blocks.add(block3.innerBlock(test.getId()));

        List<BlockWrapper> wrappers = new ArrayList<>();
        wrappers.add(block1);
        wrappers.add(block2);
        wrappers.add(block3);
        wrappers.add(block4);

        blocks.add(block4.innerBlock(test.getId()));

        doReturn(blocks).when(dao).getTestBlocks(7);
        List<BlockWrapper> blockResult = service.getTestBlocksForTest(7);
        for (BlockWrapper t : blockResult) {
            assertTrue("Getting unrelated results while trying to get all test blocks", wrappers.contains(t));
        }
        System.out.println("");
        for (BlockWrapper t : wrappers) {
            assertTrue("Didn't get all the blocks from the test", blockResult.contains(t));
        }

        QuestionService questionService = new QuestionService(qdao, adao, dao, mdao);

        TestQuestion question1 = new TestQuestion("question1", block1.innerBlock(test.getId()), cognitiveTest, manager);
        questionService.createTestQuestion(question1);
        TestQuestion question2 = new TestQuestion("question2", block2.innerBlock(test.getId()), cognitiveTest, manager);
        questionService.createTestQuestion(question2);
        TestQuestion question3 = new TestQuestion("question3", block3.innerBlock(test.getId()), cognitiveTest, manager);
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

        blockService.deleteTestBlock(15);
        blockService.deleteTestBlock(16);
        blockService.deleteTestBlock(17);
        blockService.deleteTestBlock(18);

        questionService.deleteTestQuestion(19);
        questionService.deleteTestQuestion(20);
        questionService.deleteTestQuestion(21);


        TestWrapper wrapper = new TestWrapper(cognitiveTest,wrappers);
        for (BlockWrapper w : wrappers){
            w.setQuestions(questions);
        }

        TestWrapper wrapperResult = service.createTestForTestManager(wrapper);
        assertTrue("Problem with creating test wrapper with questions", wrapper != null);
        List<BlockWrapper> testBlocks = wrapperResult.getBlocks();
        for (BlockWrapper t : testBlocks) {
            assertTrue("Getting unrelated blocks from the test wrappers", wrappers.contains(t));
            List<TestQuestion> blockQuestions = t.getQuestions();
            for (TestQuestion q : blockQuestions){
                assertTrue("Problem with inserting the questions to the test wrapper", questions.contains(q));
            }
        }
        for (BlockWrapper t : wrappers) {
            assertTrue("Didn't get all the blocks from the test wrapper", testBlocks.contains(t));
        }

        wrapper.setName("What a lovely name!");
        doReturn(blocks).when(dao).getTestBlocks(18L);
        doReturn(questions).when(bdao).getAllBlockQuestions(12L);
        doReturn(questions).when(bdao).getAllBlockQuestions(13L);
        doReturn(questions).when(bdao).getAllBlockQuestions(14L);
        doReturn(questions).when(bdao).getAllBlockQuestions(15L);
        service.updateTestForTestManager(wrapper);
        doReturn(wrapper.innerTest()).when(dao).get(18L);
        TestWrapper wrapper1 = service.findTestById(18L);
        String resName = wrapper1.getName();
        assertEquals("Problem with updating a test with questions","What a lovely name!",resName);



        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try{
            service.createTestForTestManager(new TestWrapper());
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updateTestForTestManager(new TestWrapper());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).delete(any());
        try {
            service.deleteTestForTestManager(7);
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}
    }


}