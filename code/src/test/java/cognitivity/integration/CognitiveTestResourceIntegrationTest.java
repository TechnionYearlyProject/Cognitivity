package cognitivity.integration;

import cognitivity.controllers.CognitiveTestController;
import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Before
    public void setup() {
        client = WebTestClient.bindToController(controller)
                .configureClient()
                .baseUrl(baseUrl)
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
    private static TestWrapper createCognitiveTestWrapper(boolean withQuestions) {
        CognitiveTest cognitiveTest = AbstractResourceIntegrationTest.createCognitiveTest();
        TestBlock block1 = new TestBlock(1, true, "tag1", cognitiveTest);
        TestBlock block2 = new TestBlock(2, true, "tag1", cognitiveTest);
        TestBlock block3 = new TestBlock(3, true, "tag1", cognitiveTest);
        TestBlock block4 = new TestBlock(4, true, "tag1", cognitiveTest);
        List<TestQuestion> questions1 = Collections.singletonList(createTestQuestion(1, block1, cognitiveTest));
        List<TestQuestion> questions2 = Stream.concat(questions1.stream(), Stream.of(createTestQuestion(2, block2, cognitiveTest))).collect(Collectors.toList());
        List<TestQuestion> questions3 = Stream.concat(questions2.stream(), Stream.of(createTestQuestion(3, block3, cognitiveTest))).collect(Collectors.toList());
        List<TestQuestion> questions4 = Stream.concat(questions3.stream(), Stream.of(createTestQuestion(4, block4, cognitiveTest))).collect(Collectors.toList());
        List<BlockWrapper> blockList = Arrays.asList(
                new BlockWrapper(questions1, block1),
                new BlockWrapper(questions2, block2),
                new BlockWrapper(questions3, block3),
                new BlockWrapper(questions4, block4)
        );

        return new TestWrapper(cognitiveTest, blockList);
    }

    @Test
    public void testSaveCognitiveTest() throws Exception {
        /*TestWrapper testWrapper = createCognitiveTestWrapper();
        mockMvc.perform(post("/tests/saveCognitiveTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(testWrapper)))
                .andExpect(status().isOk());*/

    }

}
