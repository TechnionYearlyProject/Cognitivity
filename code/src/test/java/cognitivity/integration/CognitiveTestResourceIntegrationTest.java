package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.controllers.CognitiveTestController;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 17/01/18.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
@Ignore
public class CognitiveTestResourceIntegrationTest extends AbstractResourceIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CognitiveTestController controller;

    @Autowired
    private Gson gson;

    @Before
    public void setup() {
        client = WebTestClient.bindToController(controller)
                .configureClient()
                .baseUrl(baseUrl)
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        this.gson = new Gson();
    }

    /**
     * Creates a TestQuestion with question = "qi" with i an integer.
     */
    private static TestQuestion createTestQuestion(int i, TestBlock block, CognitiveTest test) {
        return new TestQuestion("q" + i, 1, "a", "t", block, test, test.getManager(), 1);
    }

    /**
     * Creating a cognitive test with list of blocks with or without questions
     */
    private static TestWrapper createCognitiveTestWrapper(boolean withQuestions, long id) {
        CognitiveTest cognitiveTest = AbstractResourceIntegrationTest.createCognitiveTest(id);
        TestBlock block1 = new TestBlock(1, true, "tag1", cognitiveTest);
        TestBlock block2 = new TestBlock(2, true, "tag2", cognitiveTest);
        TestBlock block3 = new TestBlock(3, true, "tag3", cognitiveTest);
        TestBlock block4 = new TestBlock(4, true, "tag4", cognitiveTest);
        List<TestQuestion> questions1 = withQuestions ? Collections.singletonList(createTestQuestion(1, block1, cognitiveTest)) : null;
        List<TestQuestion> questions2 = withQuestions ? Stream.concat(questions1.stream(), Stream.of(createTestQuestion(2, block2, cognitiveTest))).collect(Collectors.toList()) : null;
        List<TestQuestion> questions3 = withQuestions ? Stream.concat(questions2.stream(), Stream.of(createTestQuestion(3, block3, cognitiveTest))).collect(Collectors.toList()) : null;
        List<TestQuestion> questions4 = withQuestions ? Stream.concat(questions3.stream(), Stream.of(createTestQuestion(4, block4, cognitiveTest))).collect(Collectors.toList()) : null;
        List<BlockWrapper> blockList = Arrays.asList(
                new BlockWrapper(questions1, block1),
                new BlockWrapper(questions2, block2),
                new BlockWrapper(questions3, block3),
                new BlockWrapper(questions4, block4)
        );

        return new TestWrapper(cognitiveTest, blockList);
    }

    @Test
    public void testSaveCognitiveTestWithoutQuestions() throws Exception {
        TestWrapper testWrapper = createCognitiveTestWrapper(false, 5L);
        // Calling saveCognitiveTest. This should save it in the database.
        mockMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testWrapper)))
                .andExpect(status().isOk());

        // Test is saved. Try find it in the database...
        long testId = gson.fromJson(
                mockMvc.perform((get("/tests/findTestsForTestManager"))
                        .param("managerId", "5"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$[0].blocks[0].tag", is("tag1")))
                        .andExpect(jsonPath("$[0].blocks[1].tag", is("tag2")))
                        .andExpect(jsonPath("$[0].blocks[2].tag", is("tag3")))
                        .andExpect(jsonPath("$[0].blocks[3].tag", is("tag4")))
                        .andReturn().getResponse().getContentAsString(), TestWrapper.class)
                .getId();

        // Finally delete the test
        mockMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", String.valueOf(testId)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveCognitiveTestWithQuestions() throws Exception {
        TestWrapper testWrapper = createCognitiveTestWrapper(true, 5L);
        // Calling saveCognitiveTest. This should save it in the database.
        mockMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testWrapper)))
                .andExpect(status().isOk());

        // Test is saved. Try find it in the database...
        long testId = gson.fromJson(
                mockMvc.perform((get("/tests/findTestsForTestManager"))
                        .param("managerId", "5"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$[0].blocks[0].tag", is("tag1")))
                        .andExpect(jsonPath("$[0].blocks[1].tag", is("tag2")))
                        .andExpect(jsonPath("$[0].blocks[2].tag", is("tag3")))
                        .andExpect(jsonPath("$[0].blocks[3].tag", is("tag4")))
                        .andExpect(jsonPath("$[0].blocks[0].questions[0].question", is("q1")))
                        .andExpect(jsonPath("$[0].blocks[0].questions[1].question", is("q2")))
                        .andExpect(jsonPath("$[0].blocks[0].questions[2].question", is("q3")))
                        .andExpect(jsonPath("$[0].blocks[0].questions[3].question", is("q4")))
                        .andExpect(jsonPath("$[0].blocks[1].questions[0].question", is("q1")))
                        .andExpect(jsonPath("$[0].blocks[1].questions[1].question", is("q2")))
                        .andExpect(jsonPath("$[0].blocks[2].questions[2].question", is("q3")))
                        .andExpect(jsonPath("$[0].blocks[3].questions[3].question", is("q4")))
                        .andReturn().getResponse().getContentAsString(), TestWrapper.class)
                .getId();

        // Finally delete the test
        mockMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", String.valueOf(testId)))
                .andExpect(status().isOk());
    }

}
