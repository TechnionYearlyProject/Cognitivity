package cognitivity.integration;

import cognitivity.controllers.CognitiveTestController;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by ophir on 17/01/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationTestContextConfiguration.class})
@WebAppConfiguration
@SpringBootTest
public class CognitiveTestResourceIntegrationTest extends AbstractResourceIntegrationTest {


    @Autowired
    private CognitiveTestController controller;

    @Before
    public void setup() {
        client = WebTestClient.bindToController(controller)
                .configureClient()
                .baseUrl(baseUrl)
                .build();

    }

    @Test
    public void testSaveCognitiveTest() {
        /*CognitiveTest cognitiveTest = createCognitiveTest();
                client.post().uri("tests/saveCognitiveTest")
                .body()
                .contentType(APPLICATION_JSON_UTF8)
                .conte*/
    }

}
