package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.entities.TestManager;
import cognitivity.services.TestManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.TestContextBeanConfiguration;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 19/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class})
public class TestManagerControllerTest implements RestControllerTest {

    private TestManagerController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestManagerService testManagerService;

    private TestManager testManager;

    @Before
    public void setUp() {
        Mockito.reset(testManagerService);

        controller = new TestManagerController(testManagerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        testManager = mockTestManager();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    private TestManager mockTestManager() {
        return new TestManager() {

            @Override
            public String getEmail() {
                return "email";
            }
        };
    }

    @Test
    public void findTestManagersForTestCriteriaReturnsMockedTestAnswerByTest() throws Exception {
        Mockito.when(testManagerService.findTestManagerByCreatedTest(1234)).thenReturn(testManager);

        // findTestManagersForTestCriteria is a http GET request
        mockMvc.perform(get("/test-managers/findTestManagersForTestCriteria")
                .param("testManagerId", "12345")
                .param("testId", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email", is(testManager.getEmail())));

        Mockito.verify(testManagerService, times(1)).findTestManagerByCreatedTest(1234);
        Mockito.verifyNoMoreInteractions(testManagerService);
    }

    @Test
    public void findTestManagerIdByEmailCallsServiceMethodsCorrectly() throws Exception {
        Mockito.when(testManagerService.getManagerIdByEmail("email")).thenReturn(123L);

        // findTestManagersForTestCriteria is a http GET request
        mockMvc.perform(get("/test-managers/findTestManagerIdByEmail")
                .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(123)));

        Mockito.verify(testManagerService, times(1)).getManagerIdByEmail("email");
        Mockito.verifyNoMoreInteractions(testManagerService);
    }


    @Test
    public void findTestManagersForTestCriteriaReturnsMockedTestAnswerByManager() throws Exception {
        Mockito.when(testManagerService.findTestManager(12345)).thenReturn(testManager);

        // findTestManagersForTestCriteria is a http GET request
        mockMvc.perform(get("/test-managers/findTestManagersForTestCriteria")
                .param("testManagerId", "12345")
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email", is(testManager.getEmail())));

        Mockito.verify(testManagerService, times(1)).findTestManager(12345);
        Mockito.verifyNoMoreInteractions(testManagerService);
    }

    @Test
    public void saveTestManagerCallsServiceWithCorrectParams() throws Exception {
        TestManager tm = new TestManager("email");
        // saveTestManager is a http POST request
        mockMvc.perform(post("/test-managers/saveTestManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(tm)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testManagerService, times(1)).createTestManager(Matchers.any(TestManager.class));
        Mockito.verifyNoMoreInteractions(testManagerService);
    }

    @Test
    public void updateTestManagerCallsServiceWithCorrectParams() throws Exception {
        // updateTestManager is a http POST request
        mockMvc.perform(post("/test-managers/updateTestManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testManager)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testManagerService, times(1)).updateTestManager(any(TestManager.class));
        Mockito.verifyNoMoreInteractions(testManagerService);
    }

    @Test
    public void deleteTestManagerDeletesOne() throws Exception {
        // deleteTestManager is a http DELETE request
        mockMvc.perform(delete("/test-managers/deleteTestManager")
                .param("testManagerId", "112"))
                .andExpect(status().isOk());

        Mockito.verify(testManagerService, times(1)).deleteTestManager(112);
        Mockito.verifyNoMoreInteractions(testManagerService);
    }
}
