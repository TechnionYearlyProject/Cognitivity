package cognitivity.services;

/**
 * A test class for Test block service
 * @Author - Pe'er
 * @Date - 2.2.18
 */
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


        List<TestQuestion> questions = new ArrayList<TestQuestion>();

        service.findBlockById(1);

        doReturn(new TestBlock()).when(dao).get(1L);
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

        doThrow(new org.hibernate.HibernateException("")).when(dao).getAllBlockQuestions(1);
        try {
            service.findAllBlockQuestions(1);
            assertTrue("Problem with handling with exception at findAllBlockQuestions",false);
        }catch (Exception e){}

    }


}