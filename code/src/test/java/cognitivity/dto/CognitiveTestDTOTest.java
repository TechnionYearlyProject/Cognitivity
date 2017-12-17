package cognitivity.dto;

import cognitivity.dao.CognitiveTest;
import cognitivity.dao.TestManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ophir on 17/12/17.
 */
public class CognitiveTestDTOTest implements DTOTest {

    private List<CognitiveTest> tests = new ArrayList<>();

    @Before
    public void setup() {
        TestManager manager1 = new TestManager();
        manager1.setId(1L);
        TestManager manager2 = new TestManager();
        manager2.setId(2L);
        CognitiveTest test = new CognitiveTest();
        test.setId(1L);
        test.setLastAnswered("lastAnswered");
        test.setLastModified(new Date(1L));
        test.setManager(manager1);
        test.setName("test1");
        test.setNumberOfFiledCopies(5);
        test.setNumberOfQuestions(50);
        test.setNumberOfTestees(2);
        test.setState(0);

        CognitiveTest test2 = new CognitiveTest();
        test2.setId(2L);
        test2.setLastAnswered("lastAnswered2");
        test2.setLastModified(new Date(2L));
        test2.setManager(manager2);
        test2.setName("test2");
        test2.setNumberOfFiledCopies(6);
        test2.setNumberOfQuestions(51);
        test2.setNumberOfTestees(3);
        test2.setState(1);

        tests.add(test);
        tests.add(test2);
    }

    @Override
    @Test
    public void checkMapEntity() {
        CognitiveTest test = tests.get(0);
        CognitiveTestDTO testDTO = CognitiveTestDTO.mapFromCognitiveTestEntity(test);
        Assert.assertTrue(test.getId().equals(testDTO.getId()));
        Assert.assertEquals(test.getLastAnswered(), testDTO.getLastAnswered());
        Assert.assertEquals(test.getLastModified(), testDTO.getLastModified());
        Assert.assertEquals(TestManagerDTO.mapFromTestManagerEntity(test.getManager()), testDTO.getManager());
        Assert.assertEquals(test.getName(), testDTO.getName());
        Assert.assertTrue(test.getNumberOfFiledCopies().equals(testDTO.getNumberOfFiledCopies()));
        Assert.assertTrue(test.getNumberOfQuestions().equals(testDTO.getNumberOfQuestions()));
        Assert.assertTrue(test.getNumberOfTestees().equals(testDTO.getNumberOfSubjects()));
    }

    @Override
    @Test
    public void checkMapListEntities() {
        List<CognitiveTestDTO> dtos = CognitiveTestDTO.mapFromCognitiveTestEntities(tests);
        for (int i = 0; i < tests.size(); i++) {
            CognitiveTest test = tests.get(i);
            CognitiveTestDTO testDTO = dtos.get(i);
        }
    }
}
