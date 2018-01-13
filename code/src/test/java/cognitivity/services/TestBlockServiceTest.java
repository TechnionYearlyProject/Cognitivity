package java.cognitivity.services;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.services.CognitiveTestService;
import cognitivity.services.TestBlockService;
import cognitivity.services.TestManagerService;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBlockServiceTest {

    @Test
    public void FullTest(){
        TestBlockService service = new TestBlockService();
        CognitiveTestService testService = new CognitiveTestService();
        TestManagerService managerService = new TestManagerService();

        TestManager manager = managerService.createTestManager("Asaf Lotz","Ze masirah");
        CognitiveTest test =  testService.createTestForTestManager("YYY Eize Ra'ash. Shiyo",manager, 2, 1);
        TestBlock block = service.createTestBlock(1,true,"EZ",test);

        assertNotNull("Problem with creating a test block", block);

        TestBlock result = service.findBlockById(block.getId());

        assertEquals("Problem with getting a block", result, block);

        block.setNumberOfQuestions(7);
        service.updateTestBlock(block);

        result = service.findBlockById(block.getId());
        int numericAnswer = result.getNumberOfQuestions();

        assertEquals("Problem with updating a block", numericAnswer, 7);

        service.deleteTestBlock(block.getId());

    }


}