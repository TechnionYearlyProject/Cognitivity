package cognitivity.services;

import cognitivity.dao.TestQuestionDAO;
import cognitivity.entities.*;
import cognitivity.services.CognitiveTestService;
import cognitivity.services.QuestionService;
import cognitivity.services.TestBlockService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class CognitiveTestServiceTest {


//TODO: check if we need to delete the created parameters from the DB at the end of the test.
    @Test
    public void FullTest(){
        CognitiveTestService service = new CognitiveTestService();
        TestManager manager = new TestManager();
        CognitiveTest test  = service.createTestForTestManager("testing12 12 skeedup!",manager,1,12);
        assertNotNull("Problem in making test", test);
        CognitiveTest getting = service.findTestById(test.getId());
        assertNotNull("Problem in finding the test", getting);
        test.setName("Skum toom toom toom");
        service.updateTestForTestManager(test);
        getting = service.findTestById(test.getId());
        assertEquals("Problem with updating", getting.getName(),test.getName());
        CognitiveTest test1  = service.createTestForTestManager("Man's not hot",manager,2,2);
        CognitiveTest test2  = service.createTestForTestManager("Two plus two is",manager,4,6);
        CognitiveTest test3  = service.createTestForTestManager("Minus 0ne that's",manager,3,10);
        CognitiveTest test4  = service.createTestForTestManager("Quick maths!",manager,3,17);
        List<CognitiveTest> tests = new ArrayList<CognitiveTest>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        List<CognitiveTest> result = service.findTestsForTestManager(manager.getId());
        for (CognitiveTest t : result){
            assertTrue("Getting unrelated results while trying to get all managers tests",tests.contains(t));
        }
        for (CognitiveTest t : tests){
            assertTrue("Didn't get all the tests from the manager",result.contains(t));
        }
        TestBlockService blockService = new TestBlockService();
        TestBlock block1 = blockService.createTestBlock(2,true,"tag1",test);
        TestBlock block2 = blockService.createTestBlock(5,false,"tag2",test);
        TestBlock block3 = blockService.createTestBlock(4,true,"tag3",test);
        TestBlock block4 = blockService.createTestBlock(8,false,"tag4",test);

        List<TestBlock> blocks = new ArrayList<TestBlock>();
        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);
        blocks.add(block4);

        List<TestBlock> blockResult = service.getTestBlocksForTest(test.getId());
        for (TestBlock t : blockResult){
            assertTrue("Getting unrelated results while trying to get all test blocks",blocks.contains(t));
        }
        for (TestBlock t : blocks){
            assertTrue("Didn't get all the blocks from the test",blockResult.contains(t));
        }

        QuestionService questionService = new QuestionService();

        TestQuestion question1 = new TestQuestion("question1",1,5,"bla",block1,test,manager);
        questionService.createTestQuestion(question1);
        TestQuestion question2 = new TestQuestion("question2",1,5,"bla2",block2,test,manager);
        questionService.createTestQuestion(question2);
        TestQuestion question3 = new TestQuestion("question3",1,5,"bla3",block3,test,manager);
        questionService.createTestQuestion(question3);

        List<TestQuestion> questions = new ArrayList<TestQuestion>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        List<TestQuestion> questionRes = service.getTestQuestionsForTest(test.getId());
        for (TestQuestion t : questionRes){
            assertTrue("Getting unrelated question while trying to get all test questions",questions.contains(t));
        }
        for (TestQuestion t : questions){
            assertTrue("Didn't get all the questions from the test",questionRes.contains(t));
        }


        service.deleteTestForTestManager(test.getId());
        service.deleteTestForTestManager(test1.getId());
        service.deleteTestForTestManager(test2.getId());
        service.deleteTestForTestManager(test3.getId());
        service.deleteTestForTestManager(test4.getId());

        blockService.deleteTestBlock(block1.getId());
        blockService.deleteTestBlock(block2.getId());
        blockService.deleteTestBlock(block3.getId());
        blockService.deleteTestBlock(block4.getId());

        questionService.deleteTestQuestion(question1.getId());
        questionService.deleteTestQuestion(question2.getId());
        questionService.deleteTestQuestion(question3.getId());


    }



}