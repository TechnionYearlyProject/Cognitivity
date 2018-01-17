package cognitivity.services;

import cognitivity.dao.CognitiveTestDAOimpl;
import cognitivity.dao.TestBlockDAOimpl;
import cognitivity.dao.TestManagerDAOimpl;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestManager;
import cognitivity.services.CognitiveTestService;
import cognitivity.services.TestManagerService;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class})
@SpringBootTest
public class TestManagerServiceTest {

    @Autowired
    private TestManagerDAOimpl dao;


    @Autowired
    private CognitiveTestDAOimpl tdao;

    @Autowired



    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(tdao);

    }

    /*
    For the sake of mocking:
    manager Id -1
    test Id - 2
     */
    @Test
    public void FullTest(){
        TestManagerService service = new TestManagerService(dao,tdao);
        CognitiveTestService testService = new CognitiveTestService(tdao);

        TestManager manager = service.createTestManager("Moty Luchim", "123456");

        CognitiveTest test = testService.createTestForTestManager("test1",manager,1,0);

        assertNotNull("Problem with creating a test manager", manager);

        doReturn(manager).when(dao).get(Long.valueOf(1));
        TestManager result = service.findTestManager(1);

        assertEquals("Problem with finding a test manager", result,manager);

        manager.setName("Avrasha Masait");

        service.updateTestManager(manager);
            result = service.findTestManager(1);

        assertEquals("Problem with updating a test manager", "Avrasha Masait",result.getName());

//        doReturn(manager).when(dao).get(any());
//        result = service.findTestManagerByCreatedTest(2);
//        assertEquals("Problem with finding a manager by a test", result,manager);


        CognitiveTest test1 = testService.createTestForTestManager("test13",manager,1,0);
        CognitiveTest test2 = testService.createTestForTestManager("test14",manager,1,0);
        CognitiveTest test3 = testService.createTestForTestManager("test13",manager,1,0);

        List<CognitiveTest> tests = new ArrayList<CognitiveTest>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);

        doReturn(tests).when(tdao).getCognitiveTestOfManager(1);
        List<CognitiveTest> testList = service.findTestsForTestManager(1);
        for (CognitiveTest t : testList){
            assertTrue("Getting unrelated tests while trying to get all tests from a specific manager",tests.contains(t));
        }
        for (CognitiveTest t : tests){
            assertTrue("Didn't get all the tests from a specific manager",testList.contains(t));
        }

        service.deleteTestManager(1);

        testService.deleteTestForTestManager(2);
        testService.deleteTestForTestManager(3);
        testService.deleteTestForTestManager(4);
        testService.deleteTestForTestManager(5);



    }


}