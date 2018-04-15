package cognitivity.entities;

import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

public class CognitiveTestTest {

    private static Long managerId = 3L;
    private static Long testId = 10L;

    public static TestManager createTestManager() {
        return new TestManager("onlyForTestsasafds");
    }

    public static CognitiveTest createCognitiveTest() {
        TestManager testManager = createTestManager();

        // in the application, when we push item to the db, hibernate allocats the id
        testManager.setId(managerId);
        return new CognitiveTest("testName", testManager, 3, 10, "notes", "project");
    }

    @Test
    public void gettersSettersTest() {
        CognitiveTest cognitiveTest = createCognitiveTest();
        // id check
        cognitiveTest.setId(testId);
        assertTrue(cognitiveTest.getId().equals(testId));

        // test name check
        assertTrue(cognitiveTest.getName().equals("testName"));
        cognitiveTest.setName("newName");

        // manager check
        assertTrue(cognitiveTest.getName().equals("newName"));
        assertTrue(cognitiveTest.getManager().getId().equals(managerId));
        TestManager newTestManager = new TestManager("testStealercrackme");
        newTestManager.setId(managerId + 1);
        cognitiveTest.setManager(newTestManager);
        assertTrue(cognitiveTest.getManager().getId().equals(managerId + 1));

        // state check
        assertTrue(cognitiveTest.getState() == 3);
        cognitiveTest.setState(2);
        assertTrue(cognitiveTest.getState() == 2);

        // numberOfQuestion check
        assertTrue(cognitiveTest.getNumberOfQuestions() == 10);
        cognitiveTest.setNumberOfQuestions(1);
        assertTrue(cognitiveTest.getNumberOfQuestions() == 1);

        // last answered should be null at creation
        assertTrue(cognitiveTest.getLastAnswered() == null);
        Date newDate = new Date(Calendar.getInstance().getTimeInMillis());
        cognitiveTest.setLastAnswered(newDate);
        assertTrue(cognitiveTest.getLastAnswered() == newDate);

        // LastModified check
        Date lastModified = new Date(Calendar.getInstance().getTimeInMillis());
        cognitiveTest.setLastModified(lastModified);
        assertTrue(cognitiveTest.getLastModified() == lastModified);

        // numberOfFieldCopies should be 0 at start
        assertTrue(cognitiveTest.getNumberOfFiledCopies() == 0);
        cognitiveTest.setNumberOfFiledCopies(11);
        assertTrue(cognitiveTest.getNumberOfFiledCopies() == 11);

        // numberOfSubjects should be 0 at start
        assertTrue(cognitiveTest.getNumberOfSubjects() == 0);
        cognitiveTest.setNumberOfSubjects(11);
        assertTrue(cognitiveTest.getNumberOfSubjects() == 11);
    }
}
