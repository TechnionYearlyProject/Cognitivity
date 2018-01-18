package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.entities.TestAnswer;
import cognitivity.services.TestAnswerService;
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
public class TestAnswerControllerTest implements RestControllerTest {

    private TestAnswerController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestAnswerService testAnswerService;

    private TestAnswer testAnswer;

    @Before
    public void setUp() {
        Mockito.reset(testAnswerService);

        controller = new TestAnswerController(testAnswerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        testAnswer = mockTestAnswer();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    private TestAnswer mockTestAnswer() {
        return new TestAnswer() {
            @Override
            public Integer getNumberOfClick() {
                return 11;
            }

            @Override
            public Integer getFinalAnswer() {
                return 4;
            }

            @Override
            public Integer getQuestionPlacement() {
                return 5;
            }

            @Override
            public Integer getAnswerPlacement() {
                return 6;
            }

            @Override
            public String getVerbalAnswer() {
                return "answer";
            }

            @Override
            public Boolean getQuestionWithPicture() {
                return true;
            }

            @Override
            public Boolean getTimeMeasured() {
                return false;
            }

            @Override
            public Boolean getTimeShowed() {
                return true;
            }

            @Override
            public Boolean getTesteeExit() {
                return false;
            }
        };
    }

    @Test
    public void findTestAnswerByIdReturnsMockedTestAnswer() throws Exception {
        Mockito.when(testAnswerService.findTestAnswerById(12345)).thenReturn(testAnswer);

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/test-answers/findTestAnswerById")
                .param("testAnswerId", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.numberOfClick", is(testAnswer.getNumberOfClick())))
                .andExpect(jsonPath("$.finalAnswer", is(testAnswer.getFinalAnswer())))
                .andExpect(jsonPath("$.questionPlacement", is(testAnswer.getQuestionPlacement())))
                .andExpect(jsonPath("$.answerPlacement", is(testAnswer.getAnswerPlacement())))
                .andExpect(jsonPath("$.verbalAnswer", is(testAnswer.getVerbalAnswer())))
                .andExpect(jsonPath("$.questionWithPicture", is(testAnswer.getQuestionWithPicture())))
                .andExpect(jsonPath("$.timeToAnswer", is(testAnswer.getTimeToAnswer())))
                .andExpect(jsonPath("$.timeMeasured", is(testAnswer.getTimeMeasured())))
                .andExpect(jsonPath("$.timeShowed", is(testAnswer.getTimeShowed())))
                .andExpect(jsonPath("$.testeeExit", is(testAnswer.getTesteeExit())));

        Mockito.verify(testAnswerService, times(1)).findTestAnswerById(12345);
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void findTestAnswersByQuestionIdReturnsMockedTestAnswer() throws Exception {
        Mockito.when(testAnswerService.findAllTestAnswerForAQuestion(12345)).thenReturn(Collections.singletonList(testAnswer));

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/test-answers/findTestAnswersByQuestionId")
                .param("questionId", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].numberOfClick", is(testAnswer.getNumberOfClick())))
                .andExpect(jsonPath("$[0].finalAnswer", is(testAnswer.getFinalAnswer())))
                .andExpect(jsonPath("$[0].questionPlacement", is(testAnswer.getQuestionPlacement())))
                .andExpect(jsonPath("$[0].answerPlacement", is(testAnswer.getAnswerPlacement())))
                .andExpect(jsonPath("$[0].verbalAnswer", is(testAnswer.getVerbalAnswer())))
                .andExpect(jsonPath("$[0].questionWithPicture", is(testAnswer.getQuestionWithPicture())))
                .andExpect(jsonPath("$[0].timeToAnswer", is(testAnswer.getTimeToAnswer())))
                .andExpect(jsonPath("$[0].timeMeasured", is(testAnswer.getTimeMeasured())))
                .andExpect(jsonPath("$[0].timeShowed", is(testAnswer.getTimeShowed())))
                .andExpect(jsonPath("$[0].testeeExit", is(testAnswer.getTesteeExit())));

        Mockito.verify(testAnswerService, times(1)).findAllTestAnswerForAQuestion(12345);
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void findTestAnswersBySubjectIdReturnsMockedTestAnswer() throws Exception {
        Mockito.when(testAnswerService.findTestAnswersBySubject(12345)).thenReturn(Collections.singletonList(testAnswer));

        // findTestsForTestManager is a http GET request
        mockMvc.perform(get("/test-answers/findTestAnswersBySubjectId")
                .param("subjectId", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].numberOfClick", is(testAnswer.getNumberOfClick())))
                .andExpect(jsonPath("$[0].finalAnswer", is(testAnswer.getFinalAnswer())))
                .andExpect(jsonPath("$[0].questionPlacement", is(testAnswer.getQuestionPlacement())))
                .andExpect(jsonPath("$[0].answerPlacement", is(testAnswer.getAnswerPlacement())))
                .andExpect(jsonPath("$[0].verbalAnswer", is(testAnswer.getVerbalAnswer())))
                .andExpect(jsonPath("$[0].questionWithPicture", is(testAnswer.getQuestionWithPicture())))
                .andExpect(jsonPath("$[0].timeToAnswer", is(testAnswer.getTimeToAnswer())))
                .andExpect(jsonPath("$[0].timeMeasured", is(testAnswer.getTimeMeasured())))
                .andExpect(jsonPath("$[0].timeShowed", is(testAnswer.getTimeShowed())))
                .andExpect(jsonPath("$[0].testeeExit", is(testAnswer.getTesteeExit())));

        Mockito.verify(testAnswerService, times(1)).findTestAnswersBySubject(12345);
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void saveTestAnswerCallsServiceWithCorrectParams() throws Exception {
        // saveTestAnswer is a http POST request
        mockMvc.perform(post("/test-answers/saveTestAnswer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testAnswer)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testAnswerService, times(1)).addTestAnswerForTestQuestion(any(TestAnswer.class));
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void updateTestAnswerCallsServiceWithCorrectParams() throws Exception {
        // updateTestAnswer is a http POST request
        mockMvc.perform(post("/test-answers/updateTestAnswer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testAnswer)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(testAnswerService, times(1)).updateTestAnswerForQuestion(any(TestAnswer.class));
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void deleteTestAnswerDeletesOne() throws Exception {
        // deleteTestAnswer is a http DELETE request
        mockMvc.perform(delete("/test-answers/deleteTestAnswer")
                .param("questionId", "11")
                .param("testAnswerId", "12"))
                .andExpect(status().isOk());

        Mockito.verify(testAnswerService, times(1)).deleteTestAnswerForQuestion(12);
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }

    @Test
    public void deleteTestAnswerDeletesAll() throws Exception {
        mockMvc.perform(delete("/test-answers/deleteTestAnswer")
                .param("questionId", "11")
                .param("testAnswerId", "-1"))
                .andExpect(status().isOk());

        Mockito.verify(testAnswerService, times(1)).deleteAllTestAnswersForQuestion(11);
        Mockito.verifyNoMoreInteractions(testAnswerService);
    }
}
