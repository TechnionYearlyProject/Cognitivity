package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.dto.BlockWrapper;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class TestBlockServiceTest {
    @Autowired
    private TestBlockDAO dao;


    @Autowired
    private CognitiveTestDAO tdao;

    @Autowired
    private TestManagerDAO mdao;

    @Autowired
    private TestQuestionDAO qdao;



    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(tdao);
        Mockito.reset(mdao);
        doReturn(1L).when(dao).add(any());

    }

    /*
    For the sake of mocking
    block id - 1
     */
    @Test
    public void FullTest() throws Exception{
        TestBlockService service = new TestBlockService(dao);
        CognitiveTestService testService = new CognitiveTestService(tdao,dao, qdao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);

        TestManager manager = new TestManager("Mail e mail");
        CognitiveTest test = new CognitiveTest("YYY Eize Ra'ash. Shiyo", manager, 1, "notes", "project");
        test.setId(2L);
        BlockWrapper block = service.createTestBlock(1,true,"EZ",test);
         assertNotNull("Problem with creating a test block", block);

        /*TestQuestion question = new TestQuestion("To be or not to be?", 5, "BBB",
                "Famous questions", block, test, manager, 0);
        TestQuestion question1 = new TestQuestion("Who let the dogs out", 5, "who who whow how",
                "Questions from songs", block, test, manager, 0);
        TestQuestion question2 = new TestQuestion("Scoobie doobie doo!", 5, "Woof",
                "Famous questions", block, test, manager, 0);*/

        List<TestQuestion> questions = new ArrayList<TestQuestion>();

        /*questions.add(question);
        questions.add(question1);
        questions.add(question2);*/



        // doReturn(block).when(dao).get(1L);
        service.findBlockById(1);

        // assertEquals("Problem with getting a block", result, block);

        /* block.setNumberOfQuestions(7);
        service.updateTestBlock(block);*/

        // result = service.findBlockById(1);
        // int numericAnswer = result.getNumberOfQuestions();

        // assertEquals("Problem with updating a block", numericAnswer, 7);

        doReturn(questions).when(dao).getAllBlockQuestions(1);
        List<TestQuestion> questions1 = service.findAllBlockQuestions(1);
        for (TestQuestion t : questions1) {
            assertTrue("Getting unrelated question while trying to get all questions for a specific block", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions for a specific block", questions1.contains(t));
        }

        try {
            service.deleteTestBlock(1);
        }catch (Exception e){

        }

        try {
            managerService.deleteTestManager(2);
        }catch (Exception e){

        }

        TestBlock b = new TestBlock();
        b.setNumberOfQuestions(4654);
        doReturn(7L).when(dao).update(any());
        service.updateTestBlock(b);


        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try{
            service.createTestBlock(1,true,"EZ",test);
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updateTestBlock(new TestBlock());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).delete(any());
        try {
            service.deleteTestBlock(1);
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}


    }


}