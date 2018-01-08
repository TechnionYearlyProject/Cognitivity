package cognitivity.controllers;

import cognitivity.config.TestContextBeanConfiguration;
import cognitivity.services.CognitiveTestService;
import cognitivity.web.app.config.CognitivityMvcConfiguration;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * Created by ophir on 19/12/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class, CognitivityMvcConfiguration.class})
@WebAppConfiguration
public class CognitiveTestControllerTest implements RestControllerTest {

    @Autowired
    private CognitiveTestController controller;

    private MockMvc mockMvc;

    @Autowired
    private CognitiveTestService cognitiveTestServiceMock;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CognitiveTestController()).build();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    @Test
    public void createTestForTestManagerReturnsNewlyCreatedCognitiveTest() throws Exception {

        /*when(cognitiveTestServiceMock.createTestForTestManager(Matchers.anyString())).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
                .andExpect(jsonPath("$[0].title", is("Foo")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].description", is("Lorem ipsum")))
                .andExpect(jsonPath("$[1].title", is("Bar")));

        verify(todoServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(todoServiceMock);*/
    }
}
