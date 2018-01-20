package cognitivity.controllers;

import cognitivity.TestUtil;
import cognitivity.entities.TestQuestion;
import cognitivity.services.QuestionService;
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
@SpringBootTest(classes = {TestContextBeanConfiguration.class})
public class TestQuestionControllerTest implements RestControllerTest {

    private TestQuestionController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionService questionService;

    private TestQuestion testQuestion;

    @Before
    public void setUp() {
        Mockito.reset(questionService);

        controller = new TestQuestionController(questionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        testQuestion = mockTestQuestion();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    private TestQuestion mockTestQuestion() {
        return new TestQuestion() {
            @Override
            public String getAnswer() {
                return "hey ophir";
            }

            @Override
            public Integer getQuestionType() {
                return 11;
            }

            @Override
            public String getQuestion() {
                return "question_text";
            }

            @Override
            public String getTag() {
                return "tag";
            }
        };
    }

    @Test
    public void findTestQuestionsReturnsMockedTestAnswerByTest() throws Exception {
        Mockito.when(questionService.findAllTestQuestionsFromTestId(1234)).thenReturn(Collections.singletonList(testQuestion));

        // findTestQuestionsForTestCriteriaById is a http GET request
        mockMvc.perform(get("/test-questions/findTestQuestionsForTestCriteriaById")
                .param("testManagerId", "12345")
                .param("testId", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].questionType", is(testQuestion.getQuestionType())))
                .andExpect(jsonPath("$[0].question", is(testQuestion.getQuestion())))
                .andExpect(jsonPath("$[0].answer", is(testQuestion.getAnswer())))
                .andExpect(jsonPath("$[0].tag", is(testQuestion.getTag())));

        Mockito.verify(questionService, times(1)).findAllTestQuestionsFromTestId(1234);
        Mockito.verifyNoMoreInteractions(questionService);
    }

    //findTestQuestionById
    @Test
    public void findTestQuestionByIdReturnsMockedTestAnswerByTest() throws Exception {
        Mockito.when(questionService.findTestQuestionById(1234L)).thenReturn(testQuestion);

        // findTestQuestionsForTestCriteriaById is a http GET request
        mockMvc.perform(get("/test-questions/findTestQuestionById")
                .param("testQuestionId", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.questionType", is(testQuestion.getQuestionType())))
                .andExpect(jsonPath("$.question", is(testQuestion.getQuestion())))
                .andExpect(jsonPath("$.answer", is(testQuestion.getAnswer())))
                .andExpect(jsonPath("$.tag", is(testQuestion.getTag())));

        Mockito.verify(questionService, times(1)).findTestQuestionById(1234L);
        Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void findTestQuestionsReturnsMockedTestAnswerByManager() throws Exception {
        Mockito.when(questionService.findAllTestQuestionsFromManagerId(12345)).thenReturn(Collections.singletonList(testQuestion));

        // findTestQuestionsForTestCriteriaById is a http GET request
        mockMvc.perform(get("/test-questions/findTestQuestionsForTestCriteriaById")
                .param("testManagerId", "12345")
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].questionType", is(testQuestion.getQuestionType())))
                .andExpect(jsonPath("$[0].question", is(testQuestion.getQuestion())))
                .andExpect(jsonPath("$[0].answer", is(testQuestion.getAnswer())))
                .andExpect(jsonPath("$[0].tag", is(testQuestion.getTag())));

        Mockito.verify(questionService, times(1)).findAllTestQuestionsFromManagerId(12345);
        Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void saveQuestionCallsServiceWithCorrectParams() throws Exception {
        // saveCognitiveTestQuestion is a http POST request
        mockMvc.perform(post("/test-questions/saveCognitiveTestQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testQuestion)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(questionService, times(1)).createTestQuestion(any(TestQuestion.class));
        Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void updateQuestionCallsServiceWithCorrectParams() throws Exception {
        // updateCognitiveTestQuestion is a http POST request
        mockMvc.perform(post("/test-questions/updateCognitiveTestQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testQuestion)))
                .andExpect(status().isOk());

        // todo : this will probably fail because the jackson factory will build a new object. Should maybe update equal methods?
        Mockito.verify(questionService, times(1)).updateTestQuestion(any(TestQuestion.class));
        Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void deleteCognitiveTestQuestionCallsServiceWithCorrectParams() throws Exception {
        // deleteCognitiveTestQuestion is a http DELETE request
        mockMvc.perform(delete("/test-questions/deleteCognitiveTestQuestion")
                .param("questionId", "112"))
                .andExpect(status().isOk());

        Mockito.verify(questionService, times(1)).deleteTestQuestion(112);
        Mockito.verifyNoMoreInteractions(questionService);
    }
}
