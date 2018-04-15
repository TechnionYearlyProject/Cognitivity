package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
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
public class TestManagerServiceTest {

    @Autowired
    private TestManagerDAO dao;


    @Autowired
    private CognitiveTestDAO tdao;

    @Autowired
    private TestBlockDAO bdao;

    @Autowired
    TestQuestionDAO qdao;


    @Before
    public void setup() {

        Mockito.reset(dao);
        Mockito.reset(tdao);
        Mockito.reset(bdao);

    }

    /*
    For the sake of mocking:
    manager Id -1
    test Id - 2
    mockManager - 4
    mockTest - 5
     */
    @Test
    public void FullTest()throws Exception{
        TestManagerService service = new TestManagerService(dao,tdao);
        CognitiveTestService testService = new CognitiveTestService(tdao, bdao, qdao);

        TestManager m = new TestManager("Safafafa");
        TestManager manager = service.createTestManager(m);

        assertNotNull("Problem with creating a test manager", manager);

        CognitiveTest test = new CognitiveTest("test1", manager, 0, "notes", "project");

        doReturn(manager).when(dao).get(Long.valueOf(1));
        TestManager result = service.findTestManager(1);

        assertEquals("Problem with finding a test manager", result,manager);

        manager.setEmail("Avrasha Masait");

        service.updateTestManager(manager);
            result = service.findTestManager(1);

        assertEquals("Problem with updating a test manager", "Avrasha Masait",result.getEmail());

        TestManager mockManager = new TestManager("Mi Micha");
        mockManager.setId(4L);
        CognitiveTest mockTest = new CognitiveTest();
        mockTest.setManager(mockManager);
        mockTest.setId(5L);
        doReturn(mockTest).when(tdao).get(2L);
        doReturn(manager).when(dao).get(4L);
        result = service.findTestManagerByCreatedTest(2);
        assertEquals("Problem with finding a manager by a test", result,manager);


        CognitiveTest test1 = new CognitiveTest("test13", manager, 0, "notes", "project");
        CognitiveTest test2 = new CognitiveTest("test14", manager, 0, "notes", "project");
        CognitiveTest test3 = new CognitiveTest("test13", manager, 0, "notes", "project");

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

        TestManager manager1 = new TestManager("WachedEmail@MashehuMetoraf!");
        manager1.setId(89L);

        doReturn(89L).when(dao).getIdFromEmail("WachedEmail@MashehuMetoraf!");
        long res = service.getManagerIdByEmail("WachedEmail@MashehuMetoraf!");

        assertEquals("Problem with getting manager Id by Email", 89L, res);

        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try{
            service.createTestManager(new TestManager());
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updateTestManager(new TestManager());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).delete(any());
        try {
            service.deleteTestManager(7);
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}

    }


}