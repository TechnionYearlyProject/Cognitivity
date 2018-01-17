package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestWrapper;
import cognitivity.services.CognitiveTestService;
import cognitivity.web.app.config.HibernateBeanConfiguration;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 19/12/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
@WebAppConfiguration
public class CognitiveTestControllerTest implements RestControllerTest {

    private CognitiveTestController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CognitiveTestService cognitiveTestServiceMock;

    @Before
    public void setUp() {
        Mockito.reset(cognitiveTestServiceMock);

        controller = new CognitiveTestController(cognitiveTestServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    private static CognitiveTest buildTest(String ct, String email, int s, int nq) {
        TestManager tm = new TestManager(email);
        return new CognitiveTest(ct, tm, s, nq);
    }

    @Test
    public void findTestsForTestManagerReturnsListOfTests() throws Exception {
        TestWrapper cognitiveTest1 = new TestWrapper(buildTest("ct1", "em1", 1, 1));
        TestWrapper cognitiveTest2 = new TestWrapper(buildTest("ct2", "em2", 2, 2));

        Mockito.when(cognitiveTestServiceMock.findTestsForTestManager(Matchers.anyLong())).thenReturn(Arrays.asList(cognitiveTest1, cognitiveTest2));

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/tests/findTestsForTestManager")
                .param("managerId", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ct1")))
                .andExpect(jsonPath("$[0].state", is(1)))
                .andExpect(jsonPath("$[0].numberOfQuestions", is(1)))
                .andExpect(jsonPath("$[1].name", is("ct2")))
                .andExpect(jsonPath("$[1].state", is(2)))
                .andExpect(jsonPath("$[1].numberOfQuestions", is(2)));

        Mockito.verify(cognitiveTestServiceMock, times(1)).findTestsForTestManager(Matchers.anyLong());
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void saveCognitiveTestCallsServiceWithCorrectParams() throws Exception {
        TestManager tm = new TestManager("email");
        CognitiveTest cognitiveTest = new CognitiveTest("test", tm, 11, 30);

        // createTestForTestManager is a http POST request
        mockMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cognitiveTest)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(cognitiveTestServiceMock, times(1)).createTestForTestManager(Matchers.any(CognitiveTest.class));
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void updateCognitiveTestCallsServiceWithCorrectParams() throws Exception {
        TestWrapper cognitiveTest = new TestWrapper(buildTest("test", "email", 12, 41));

        // updateCognitiveTest is a http POST request
        mockMvc.perform(post("/tests/updateCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cognitiveTest)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(cognitiveTestServiceMock, times(1)).updateTestForTestManager(cognitiveTest);
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void deleteCognitiveTestCallServiceWithCorrectParams() throws Exception {
        // updateCognitiveTest is a http POST request
        mockMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", "11"))
                .andExpect(status().isOk());

        Mockito.verify(cognitiveTestServiceMock, times(1)).deleteTestForTestManager(11);
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }
}
