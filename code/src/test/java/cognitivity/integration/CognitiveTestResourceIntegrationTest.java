package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.controllers.CognitiveTestController;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cognitivity.integration.TestManagerResourceIntegrationTest.deleteTestManager;
import static cognitivity.integration.TestManagerResourceIntegrationTest.saveTestManager;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 17/01/18.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
public class CognitiveTestResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CognitiveTestController controller;

    @Before
    public void setup() {
        super.setup();
    }


    private TestManager manager;
    private CognitiveTest cognitiveTest;

    /**
     * Creates a TestQuestion with question = "qi" with i an integer.
     */
    private static TestQuestion createTestQuestion(int i, TestBlock block, CognitiveTest test) {
        return new TestQuestion("q" + i, 1, "a", "t", block, test, test.getManager(), 1);
    }

    /**
     * Creating a cognitive test with list of blocks with or without questions
     */
    private TestWrapper createCognitiveTestWrapper(boolean withQuestions, long id) throws Exception {
        manager = createTestManager(id);
        cognitiveTest = AbstractResourceIntegrationTest.createCognitiveTest(manager);
        cognitiveTest.setId(5L);
        TestBlock block1 = new TestBlock(1, true, "tag1", cognitiveTest);
        block1.setId(1L);
        TestBlock block2 = new TestBlock(2, true, "tag2", cognitiveTest);
        block2.setId(2L);
        TestBlock block3 = new TestBlock(3, true, "tag3", cognitiveTest);
        block3.setId(3L);
        TestBlock block4 = new TestBlock(4, true, "tag4", cognitiveTest);
        block4.setId(4L);
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
        // In order to save test, must save manager first.
        manager = createTestManager(1L);
        long managerId = saveTestManager(manager, objectMapper, testManagerMvc);

        TestWrapper testWrapper = createCognitiveTestWrapper(false, managerId);
        // Calling saveCognitiveTest. This should save it in the database.
        String testId = String.valueOf(gson.fromJson(
                cognitiveTestMvc.perform(post("/tests/saveCognitiveTest")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(testWrapper)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestWrapper.class).getId());

        System.out.println("testId is : " + testId);

        // Test is saved. Try find it in the database...
        cognitiveTestMvc.perform((get("/tests/findTestsForTestManager"))
                .param("managerId", String.valueOf(testId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("5")))
                .andExpect(jsonPath("$[0].blocks[0].tag", is("tag1")))
                .andExpect(jsonPath("$[0].blocks[1].tag", is("tag2")))
                .andExpect(jsonPath("$[0].blocks[2].tag", is("tag3")))
                .andExpect(jsonPath("$[0].blocks[3].tag", is("tag4")));

        // Finally delete the test
        cognitiveTestMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", testId))
                .andExpect(status().isOk());

        // Deleting test manager
        deleteTestManager(String.valueOf(managerId), objectMapper, testManagerMvc);
    }

    @Test
    public void testSaveCognitiveTestWithQuestions() throws Exception {
        TestWrapper testWrapper = createCognitiveTestWrapper(true, 5L);
        // Calling saveCognitiveTest. This should save it in the database.
        cognitiveTestMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testWrapper)))
                .andExpect(status().isOk());

        // Test is saved. Try find it in the database...
        long testId = gson.fromJson(
                cognitiveTestMvc.perform((get("/tests/findTestsForTestManager"))
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
        cognitiveTestMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", String.valueOf(testId)))
                .andExpect(status().isOk());
    }

}
