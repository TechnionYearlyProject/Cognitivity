package cognitivity.integration;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by ophir on 17/01/18.
 */
public class AbstractResourceIntegrationTest {

    public static TestManager createTestManager() {
        return new TestManager("email");
    }

    public static CognitiveTest createCognitiveTest() {
        return new CognitiveTest("test", createTestManager(), 1, 2);
    }

    protected WebTestClient client;

    protected static final String baseUrl = "localhost:8181/";

}
