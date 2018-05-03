package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.TestQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static cognitivity.integration.CognitiveTestResourceIntegrationTest.saveCognitiveTest;
import static cognitivity.integration.TestManagerResourceIntegrationTest.saveTestManager;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 20/01/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
@Ignore("This test class should not run - it doesn't test an optional behaviour of the system")
public class TestQuestionResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        super.setup();
    }

    private TestQuestion question;

    public static long saveTestQuestion(TestQuestion question, ObjectMapper objectMapper, MockMvc mockMvc, MockMvc managerMockMvc, MockMvc testMvc) throws Exception {
        long managerId = saveTestManager(question.getTestManager(), objectMapper, managerMockMvc);
        question.getTestManager().setId(managerId);
        TestWrapper testWrapper = new TestWrapper(question.getCognitiveTest());
        testWrapper.getBlocks().add(new BlockWrapper(question.getTestBlock()));
        long testId = saveCognitiveTest(testWrapper, objectMapper, testMvc);
        question.getCognitiveTest().setId(testId);
        //long blockId = saveTestBlock()
        return gson.fromJson(
                mockMvc.perform(post("/test-questions/saveCognitiveTestQuestion")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(question)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestQuestion.class).getId();
    }

    public static void deleteTestQuestion(String questionId, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        mockMvc.perform(delete("/test-questions/deleteCognitiveTestQuestion")
                .param("questionId", questionId))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveTestSubject() throws Exception {
        question = createTestQuestion(9L);
        // Save TestQuestion...
        long questionId = saveTestQuestion(question, objectMapper, testQuestionMvc, testManagerMvc, cognitiveTestMvc);
        question.setId(questionId);

        // Question is saved. Try find it in the database...
        testQuestionMvc.perform((get("/test-questions/findTestQuestionById"))
                .param("testQuestionId", String.valueOf(questionId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.question", is("q1")))
                .andExpect(jsonPath("$.questionType", is(1)))
                .andExpect(jsonPath("$.answer", is("a1")))
                .andExpect(jsonPath("$.tag", is("tag1")));

        // Finally delete the question...
        deleteTestQuestion(String.valueOf(questionId), objectMapper, testQuestionMvc);
    }

    @Test
    public void testUpdateTestSubject() throws Exception {
        question = createTestQuestion(9L);
        // Save TestQuestion...
        long questionId = saveTestQuestion(question, objectMapper, testQuestionMvc, testManagerMvc, cognitiveTestMvc);
        question.setId(questionId);

        // Question is saved. Try find it in the database...
        testQuestionMvc.perform((get("/test-questions/findTestQuestionById"))
                .param("testQuestionId", String.valueOf(questionId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.question", is("q1")))
                .andExpect(jsonPath("$.questionType", is(1)))
                .andExpect(jsonPath("$.answer", is("a1")))
                .andExpect(jsonPath("$.tag", is("tag1")));

        question.setQuestion("q2");

        // Call to update...
        testQuestionMvc.perform(post("/test-questions/updateCognitiveTestQuestion")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(question)))
                .andExpect(status().isOk());

        // Check that it is updated...
        testQuestionMvc.perform((get("/test-questions/findTestQuestionById"))
                .param("testQuestionId", String.valueOf(questionId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.question", is("q2")))
                .andExpect(jsonPath("$.questionType", is(2)))
                .andExpect(jsonPath("$.answer", is("a2")))
                .andExpect(jsonPath("$.tag", is("tag2")));


        // Finally delete the question...
        deleteTestQuestion(String.valueOf(questionId), objectMapper, testQuestionMvc);
    }
}
