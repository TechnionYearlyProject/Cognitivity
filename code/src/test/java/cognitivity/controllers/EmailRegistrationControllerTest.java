package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.services.EmailRegistrationService;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.TestContextBeanConfiguration;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ophir on 16/01/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class},
        locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
@SpringBootTest
public class EmailRegistrationControllerTest implements RestControllerTest {


    private EmailRegistrationController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailRegistrationService emailRegistrationServiceMock;

    @Before
    public void setUp() {
        Mockito.reset(emailRegistrationServiceMock);

        controller = new EmailRegistrationController(emailRegistrationServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    @Test
    public void findEmailCallsServiceMethodCorrectly() throws Exception {
        Mockito.when(emailRegistrationServiceMock.findEmailId("email")).thenReturn(123L);

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/emails/findEmailId")
                .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        Mockito.verify(emailRegistrationServiceMock, times(1)).findEmailId("email");
        Mockito.verifyNoMoreInteractions(emailRegistrationServiceMock);
    }

    @Test
    public void saveEmailEntryCallsServiceMethodCorrectly() throws Exception {

        // createTestForTestManager is a http POST request
        mockMvc.perform(post("/emails/saveEmail")
                .param("email", "email"))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(emailRegistrationServiceMock, times(1)).createEmailEntry("email");
        Mockito.verifyNoMoreInteractions(emailRegistrationServiceMock);
    }

    @Test
    public void deleteEmailEntryCallsServiceMethodCorrectly() throws Exception {
        // updateCognitiveTest is a http POST request
        mockMvc.perform(delete("/emails/deleteEmail")
                .param("email", "email"))
                .andExpect(status().isOk());

        Mockito.verify(emailRegistrationServiceMock, times(1)).deleteEmailEntry("email");
        Mockito.verifyNoMoreInteractions(emailRegistrationServiceMock);
    }

}
