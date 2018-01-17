package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class},
        locations = {"classpath:spring/test-context.xml", "classpath:spring/test-dispatcher-servlet.xml"})
@SpringBootTest
public class TestBlockServiceTest {
    @Autowired
    private TestBlockDAO dao;


    @Autowired
    private CognitiveTestDAO tdao;

    @Autowired
    private TestManagerDAO mdao;



    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(tdao);
        Mockito.reset(mdao);

    }

    /*
    For the sake of mocking
    block id - 1
     */
    @Test
    public void FullTest(){
        TestBlockService service = new TestBlockService(dao);
        CognitiveTestService testService = new CognitiveTestService(tdao,dao);
        TestManagerService managerService = new TestManagerService(mdao,tdao);

        TestManager manager = new TestManager("Mail e mail");
        CognitiveTest test = new CognitiveTest("YYY Eize Ra'ash. Shiyo", manager, 2, 1);
        TestBlock block = service.createTestBlock(1,true,"EZ",test);

        assertNotNull("Problem with creating a test block", block);

        doReturn(block).when(dao).get(Long.valueOf(1));
        TestBlock result = service.findBlockById(1);

        assertEquals("Problem with getting a block", result, block);

        block.setNumberOfQuestions(7);
        service.updateTestBlock(block);

        result = service.findBlockById(1);
        int numericAnswer = result.getNumberOfQuestions();

        assertEquals("Problem with updating a block", numericAnswer, 7);

        service.deleteTestBlock(1);

        managerService.deleteTestManager(2);

        testService.deleteTestForTestManager(3);

    }


}