package cognitivity.integration;

import cognitivity.controllers.CognitiveTestController;
import cognitivity.controllers.TestManagerController;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;

/**
 * Created by ophir on 17/01/18.
 */


/**
 * Integration Tests base class. These tests should only run with the database set up.
 * They check that the data is passed via each controller (by sending a get/post message)
 * to the database (and is saved/pulled to/from it).
 */
public class AbstractResourceIntegrationTest {

    protected MockMvc cognitiveTestMvc;
    protected MockMvc testManagerMvc;

    @Autowired
    protected CognitiveTestController cognitiveTestController;

    @Autowired
    protected TestManagerController testManagerController;

    protected static Gson gson;

    @Before
    public void setup() {
        cognitiveTestMvc = MockMvcBuilders.standaloneSetup(cognitiveTestController).build();
        testManagerMvc = MockMvcBuilders.standaloneSetup(testManagerController).build();

        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));

        gson = builder.create();
    }

    public static TestManager createTestManager(long id) {
        TestManager testManager = new TestManager("email");
        testManager.setId(id);
        return testManager;
    }

    public static CognitiveTest createCognitiveTest(TestManager manager) {
        return new CognitiveTest("test", manager, 1, 2);
    }

    protected WebTestClient client;

    protected static final String baseUrl = "localhost:8181/";

}
