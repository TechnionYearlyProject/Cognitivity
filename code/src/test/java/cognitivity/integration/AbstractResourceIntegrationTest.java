package cognitivity.integration;

import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by ophir on 17/01/18.
 */
public class AbstractResourceIntegrationTest {

    protected WebTestClient client;

    protected static final String baseUrl = "localhost:8181/";

}
