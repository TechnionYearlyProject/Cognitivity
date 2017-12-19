package cognitivity.controllers;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestContextBootstrapper;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by ophir on 19/12/17.
 */

@SpringBootTest(classes={TestConfiguration.class})
@RunWith(SpringRunner.class)
public class CognitiveTestControllerTest implements RestControllerTest {

    @Autowired
    private CognitiveTestController controller;

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }
}
