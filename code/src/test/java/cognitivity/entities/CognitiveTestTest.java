package cognitivity.entities;

import org.junit.Test;

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
        return new CognitiveTest("testName", testManager, 10, "notes", "project");
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

        // numberOfQuestion check
        assertTrue(cognitiveTest.getNumberOfQuestions() == 10);
        cognitiveTest.setNumberOfQuestions(1);
        assertTrue(cognitiveTest.getNumberOfQuestions() == 1);

    }
}
