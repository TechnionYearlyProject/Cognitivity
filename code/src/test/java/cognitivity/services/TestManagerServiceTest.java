package java.cognitivity.services;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestManager;
import cognitivity.services.CognitiveTestService;
import cognitivity.services.TestManagerService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestManagerServiceTest {

    @Test
    public void FullTest(){
        TestManagerService service = new TestManagerService();
        CognitiveTestService testService = new CognitiveTestService();

        TestManager manager = service.createTestManager("Moty Luchim", "123456");

        CognitiveTest test = testService.createTestForTestManager("test1",manager,1,0);

        assertNotNull("Problem with creating a test manager", manager);

        TestManager result = service.findTestManager(manager.getId());

        assertEquals("Problem with finding a test manager", result,manager);

        manager.setName("Avrasha Masait");

        service.updateTestManager(manager);
        result = service.findTestManager(manager.getId());

        assertEquals("Problem with updating a test manager", "Avrasha Masait",result.getName());

        result = service.findTestManagerByCreatedTest(test.getId());
        assertEquals("Problem with finding a manager by a test", result,manager);


        CognitiveTest test1 = testService.createTestForTestManager("test13",manager,1,0);
        CognitiveTest test2 = testService.createTestForTestManager("test14",manager,1,0);
        CognitiveTest test3 = testService.createTestForTestManager("test13",manager,1,0);

        List<CognitiveTest> tests = new ArrayList<CognitiveTest>();
        tests.add(test);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);

        List<CognitiveTest> testList = service.findTestsForTestManager(manager.getId());
        for (CognitiveTest t : testList){
            assertTrue("Getting unrelated tests while trying to get all tests from a specific manager",tests.contains(t));
        }
        for (CognitiveTest t : tests){
            assertTrue("Didn't get all the tests from a specific manager",testList.contains(t));
        }

        service.deleteTestManager(manager.getId());



    }


}