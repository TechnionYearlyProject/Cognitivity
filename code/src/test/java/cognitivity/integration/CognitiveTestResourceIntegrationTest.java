package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;
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
@Ignore
public class CognitiveTestResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

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
        return new TestQuestion("q" + i, block, test, test.getManager());
    }

    /**
     * Creating a cognitive test with list of blocks with or without questions
     */
    private TestWrapper createCognitiveTestWrapper(boolean withQuestions, long id) throws Exception {
        manager = createTestManager(id);
        cognitiveTest = AbstractResourceIntegrationTest.createCognitiveTest(manager);
        cognitiveTest.setId(77L);
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

        // Test is saved. Try find it in the database...
        cognitiveTestMvc.perform((get("/tests/findTestsForTestManagerWithoutQuestions"))
                .param("managerId", String.valueOf(managerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(Integer.valueOf(testId))))
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

    public static long saveCognitiveTest(TestWrapper testWrapper, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        return gson.fromJson(
                mockMvc.perform(post("/tests/saveCognitiveTest")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(testWrapper)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(), TestWrapper.class).getId();
    }

    private List<BlockWrapper> getNewBlockWrappers() {
        Random rand = new Random();
//        manager = new TestManager("must have an email to get in the db");
//        cognitiveTest = new CognitiveTest("Man's not hot", manager, 2, 2);

        int numOfBlocks = 15;
        //random number of questions in each block
        int maxNumberOfQuestionsInBlock = 100;
        TestBlock testBlock[] = new TestBlock[numOfBlocks];
        List<TestQuestion> questionsPerBlock[] = new ArrayList[numOfBlocks];
        List<BlockWrapper> blockWrappers = new ArrayList<>();
        for (int i = 0; i < numOfBlocks; i++) {
            testBlock[i] = new TestBlock(0, true, "pag" + i, cognitiveTest);
            questionsPerBlock[i] = new ArrayList<>();
            int numOfQuestions = rand.nextInt(maxNumberOfQuestionsInBlock);
            for (int j = 0; j < numOfQuestions; j++) {
                questionsPerBlock[i].add(new TestQuestion("q " + j, testBlock[i],
                        cognitiveTest, manager));
            }

            blockWrappers.add(new BlockWrapper(questionsPerBlock[i], testBlock[i]));
        }


        return blockWrappers;

    }

    @Test
    public void testSaveCognitiveTestWithQuestions() throws Exception {
        // In order to save test, must save manager first.
        manager = createTestManager(1L);
        long managerId = saveTestManager(manager, objectMapper, testManagerMvc);

        TestWrapper testWrapper = createCognitiveTestWrapper(true, managerId);
        // Calling saveCognitiveTest. This should save it in the database.
        String testId = String.valueOf(saveCognitiveTest(testWrapper, objectMapper, cognitiveTestMvc));

        // Test is saved. Try find it in the database...
        cognitiveTestMvc.perform((get("/tests/findTestsForTestManagerWithoutQuestions"))
                .param("managerId", String.valueOf(managerId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(Integer.valueOf(testId))))
                .andExpect(jsonPath("$[0].blocks[0].tag", is("tag1")))
                .andExpect(jsonPath("$[0].blocks[1].tag", is("tag2")))
                .andExpect(jsonPath("$[0].blocks[2].tag", is("tag3")))
                .andExpect(jsonPath("$[0].blocks[3].tag", is("tag4")))
                .andExpect(jsonPath("$[0].blocks[0].questions[0].question", is("q1")))
                .andExpect(jsonPath("$[0].blocks[1].questions[0].question", is("q1")))
                .andExpect(jsonPath("$[0].blocks[1].questions[1].question", is("q2")))
                .andExpect(jsonPath("$[0].blocks[2].questions[0].question", is("q1")))
                .andExpect(jsonPath("$[0].blocks[2].questions[1].question", is("q2")))
                .andExpect(jsonPath("$[0].blocks[2].questions[2].question", is("q3")))
                .andExpect(jsonPath("$[0].blocks[3].questions[0].question", is("q1")))
                .andExpect(jsonPath("$[0].blocks[3].questions[1].question", is("q2")))
                .andExpect(jsonPath("$[0].blocks[3].questions[2].question", is("q3")))
                .andExpect(jsonPath("$[0].blocks[3].questions[3].question", is("q4")));

        // Finally delete the test
        cognitiveTestMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", testId))
                .andExpect(status().isOk());

        // Deleting test manager
        deleteTestManager(String.valueOf(managerId), objectMapper, testManagerMvc);
    }

    @Test
    public void testUpdateCognitiveTest() throws Exception {
        // In order to save test, must save manager first.
        manager = createTestManager(1L);
        long managerId = saveTestManager(manager, objectMapper, testManagerMvc);
        manager.setId(managerId);

        TestWrapper testWrapper = createCognitiveTestWrapper(false, managerId);
        // Calling saveCognitiveTest. This should save it in the database.
        String testId = String.valueOf(gson.fromJson(
                cognitiveTestMvc.perform(post("/tests/saveCognitiveTest")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(testWrapper)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestWrapper.class).getId());

        // Test is saved. Try find it in the database...
        cognitiveTestMvc.perform((get("/tests/findTestsForTestManagerWithoutQuestions"))
                .param("managerId", String.valueOf(managerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        // Update some fields.
        testWrapper.setId(Long.valueOf(testId));
        testWrapper.setName("test2");
        testWrapper.setNumberOfQuestions(55);

        // And call to update...
        testId = String.valueOf(gson.fromJson(cognitiveTestMvc.perform((post("/tests/updateCognitiveTest"))
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(testWrapper)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestWrapper.class).getId());

        // Check if it was updated...
        cognitiveTestMvc.perform((get("/tests/findTestsForTestManager"))
                .param("managerId", String.valueOf(managerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is("test2")))
                .andExpect(jsonPath("$[0].numberOfQuestions", is(55)));

        // Change the block list and questionList
        testWrapper.setId(Long.valueOf(testId));
        List<BlockWrapper> blockWrappers = getNewBlockWrappers();
        testWrapper.setBlocks(blockWrappers);

        //update once again
        testId = String.valueOf(gson.fromJson(cognitiveTestMvc.perform((post("/tests/updateCognitiveTest"))
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(testWrapper)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestWrapper.class).getId());

        //check the result
        ResultActions updatedResult = cognitiveTestMvc.perform((get("/tests/findTestsForTestManagerWithoutQuestions"))
                .param("managerId", String.valueOf(managerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
        System.out.println(updatedResult.toString());
        int blockIdx = 0;
        for (BlockWrapper blockWrapper : blockWrappers) {
            updatedResult.andExpect(
                    jsonPath("$[0].blocks[" + blockIdx + "].tag",
                            is(blockWrapper.getTag())));
            int questionIdx = 0;
            for (TestQuestion testQuestion : blockWrapper.getQuestions()) {
                updatedResult.andExpect(
                        jsonPath("$[0].blocks[" + blockIdx + "].questions[" + questionIdx + "].question",
                                is(testQuestion.getQuestion())));
                questionIdx++;
            }
            blockIdx++;
        }

        // Finally delete the test
        cognitiveTestMvc.perform(delete("/tests/deleteCognitiveTest")
                .param("testId", testId))
                .andExpect(status().isOk());

        // Deleting test manager
        deleteTestManager(String.valueOf(managerId), objectMapper, testManagerMvc);
    }

}
