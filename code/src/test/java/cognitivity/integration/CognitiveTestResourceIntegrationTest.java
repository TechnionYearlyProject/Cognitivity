package cognitivity.integration;

import cognitivity.controllers.CognitiveTestController;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import config.IntegrationTestContextConfiguration;
import config.TestContextBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static cognitivity.TestUtil.APPLICATION_JSON_UTF8;

/**
 * Created by ophir on 17/01/18.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
@Ignore
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
        /*TestWrapper cognitiveTest = new TestWrapper(createCognitiveTest());
        client.post().uri("tests/saveCognitiveTest")
                .body(cognitiveTest, CognitiveTest.class)
                .contentType(APPLICATION_JSON_UTF8)
                .conte*/
    }

}
