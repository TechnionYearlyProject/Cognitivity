package cognitivity.controllers;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by ophir on 19/12/17.
 */

// @SpringBootTest(classes={TestConfiguration.class})
// @RunWith(SpringRunner.class)
public class CognitiveTestControllerTest implements RestControllerTest {

    @Autowired
    private CognitiveTestController controller;

    //@Autowired private MockMvc mvc;

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }
}
