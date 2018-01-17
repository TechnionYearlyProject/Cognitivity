package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.entities.TestSubject;
import cognitivity.services.TestSubjectService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 19/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class TestSubjectControllerTest implements RestControllerTest {

    private TestSubjectController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestSubjectService testSubjectService;

    private TestSubject testSubject;

    @Before
    public void setUp() {
        Mockito.reset(testSubjectService);

        controller = new TestSubjectController(testSubjectService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        testSubject = mockTestSubject();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    private TestSubject mockTestSubject() {
        return new TestSubject() {
            @Override
            public String getName() {
                return "name";
            }

            @Override
            public String getBrowser() {
                return "firefox";
            }

            @Override
            public String getIpAddress() {
                return "123";
            }
        };
    }

    @Test
    public void findTestSubjectsForTestCriteriaReturnsMockedTestAnswerByTestId() throws Exception {
        Mockito.when(testSubjectService.findTestSubjectsWhoParticipatedInTest(1234)).thenReturn(Collections.singletonList(testSubject));

        // findTestSubjectsForTestCriteria is a http GET request
        mockMvc.perform(get("/test-subjects/findTestSubjectsForTestCriteria")
                .param("testSubjectId", "12345")
                .param("testId", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is(testSubject.getName())))
                .andExpect(jsonPath("$[0].browser", is(testSubject.getBrowser())))
                .andExpect(jsonPath("$[0].ipAddress", is(testSubject.getIpAddress())));

        Mockito.verify(testSubjectService, times(1)).findTestSubjectsWhoParticipatedInTest(1234);
        Mockito.verifyNoMoreInteractions(testSubjectService);
    }

    @Test
    public void findTestManagersForTestCriteriaReturnsMockedTestAnswerBySubjectId() throws Exception {
        Mockito.when(testSubjectService.findTestSubject(12345)).thenReturn(testSubject);

        // findTestSubjectsForTestCriteria is a http GET request
        mockMvc.perform(get("/test-subjects/findTestSubjectsForTestCriteria")
                .param("testSubjectId", "12345")
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is(testSubject.getName())))
                .andExpect(jsonPath("$[0].browser", is(testSubject.getBrowser())))
                .andExpect(jsonPath("$[0].ipAddress", is(testSubject.getIpAddress())));

        Mockito.verify(testSubjectService, times(1)).findTestSubject(12345);
        Mockito.verifyNoMoreInteractions(testSubjectService);
    }

    @Test
    public void saveTestSubjectCallsServiceWithCorrectParams() throws Exception {
        TestSubject testSubject = new TestSubject("name", "123", "firefox");
        // saveTestSubject is a http POST request
        mockMvc.perform(post("/test-subjects/saveTestSubject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testSubject)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testSubjectService, times(1)).createTestSubject(testSubject);
        Mockito.verifyNoMoreInteractions(testSubjectService);
    }

    @Test
    public void updateTestSubjectCallsServiceWithCorrectParams() throws Exception {
        // updateTestSubject is a http POST request
        mockMvc.perform(post("/test-subjects/updateTestSubject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testSubject)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testSubjectService, times(1)).updateTestSubject(any(TestSubject.class));
        Mockito.verifyNoMoreInteractions(testSubjectService);
    }

    @Test
    public void deleteTestManagerDeletesOne() throws Exception {
        // deleteTestSubject is a http DELETE request
        mockMvc.perform(delete("/test-subjects/deleteTestSubject")
                .param("testSubjectId", "112"))
                .andExpect(status().isOk());

        Mockito.verify(testSubjectService, times(1)).deleteTestSubject(112);
        Mockito.verifyNoMoreInteractions(testSubjectService);
    }
}
