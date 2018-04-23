package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import cognitivity.services.CognitiveTestService;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 19/12/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class})
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

    private static CognitiveTest buildTest(String ct, String email, int nq) {
        TestManager tm = new TestManager(email);
        CognitiveTest cognitiveTest = new CognitiveTest(ct, tm, nq, "notes", "project");
        cognitiveTest.setId(1L);
        return cognitiveTest;
    }

    @Test
    public void findTestByTestId() throws Exception {
        CognitiveTest test1 = buildTest("ct1", "em1", 1);
        TestWrapper cognitiveTest1 = new TestWrapper(test1);

        Mockito.when(cognitiveTestServiceMock.findCognitiveTestById(Matchers.anyLong()))
                .thenReturn(cognitiveTest1);

        mockMvc.perform(get("/tests/findCognitiveTestById")
                .param("testId", "123456"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("ct1")))
                .andExpect(jsonPath("$.numberOfQuestions", is(1)))
                .andExpect(jsonPath("$.notes", is("notes")))
                .andExpect(jsonPath("$.project", is("project")));

        Mockito.verify(cognitiveTestServiceMock, times(1)).findCognitiveTestById(123456);
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void findTestsForTestManagerReturnsListOfTestsWithoutQuestions() throws Exception {
        CognitiveTest test1 = buildTest("ct1", "em1", 1);
        TestWrapper cognitiveTest1 = new TestWrapper(test1);
        CognitiveTest test2 = buildTest("ct2", "em2", 2);
        TestWrapper cognitiveTest2 = new TestWrapper(test2);

        Mockito.when(cognitiveTestServiceMock.findTestsForTestManagerWithoutQuestions(Matchers.anyLong())).thenReturn(Arrays.asList(test1, test2));

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/tests/findTestsForTestManagerWithoutQuestions")
                .param("managerId", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ct1")))
                .andExpect(jsonPath("$[0].numberOfQuestions", is(1)))
                .andExpect(jsonPath("$[0].notes", is("notes")))
                .andExpect(jsonPath("$[0].project", is("project")))
                .andExpect(jsonPath("$[1].name", is("ct2")))
                .andExpect(jsonPath("$[1].numberOfQuestions", is(2)))
                .andExpect(jsonPath("$[1].notes", is("notes")))
                .andExpect(jsonPath("$[1].project", is("project")));

        Mockito.verify(cognitiveTestServiceMock, times(1)).findTestsForTestManagerWithoutQuestions(Matchers.anyLong());
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void saveCognitiveTestCallsServiceWithCorrectParams() throws Exception {
        TestManager tm = new TestManager("email");
        CognitiveTest cognitiveTest = new CognitiveTest("test", tm, 30, "notes", "project");
        cognitiveTest.setId(1L);

        // createTestForTestManager is a http POST request
        mockMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new TestWrapper(cognitiveTest))))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(cognitiveTestServiceMock, times(1)).createTestForTestManager(Matchers.any(TestWrapper.class));
        Mockito.verifyNoMoreInteractions(cognitiveTestServiceMock);
    }

    @Test
    public void updateCognitiveTestCallsServiceWithCorrectParams() throws Exception {
        CognitiveTest test = buildTest("test", "email", 41);

        // updateCognitiveTest is a http POST request
        mockMvc.perform(post("/tests/updateCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new TestWrapper(test))))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(cognitiveTestServiceMock, times(1)).updateTestForTestManager(Matchers.any(TestWrapper.class));
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
