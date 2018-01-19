package cognitivity.integration;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by ophir on 17/01/18.
 */


/**
 * Integration Tests base class. These tests should only run with the database set up.
 * They check that the data is passed via each controller (by sending a get/post message)
 * to the database (and is saved/pulled to/from it).
 */
public class AbstractResourceIntegrationTest {

    public static TestManager createTestManager(long id) {
        TestManager testManager = new TestManager("email");
        testManager.setId(id);
        return testManager;
    }

    public static CognitiveTest createCognitiveTest(long id) {
        return new CognitiveTest("test", createTestManager(id), 1, 2);
    }

    protected WebTestClient client;

    protected static final String baseUrl = "localhost:8181/";

}
