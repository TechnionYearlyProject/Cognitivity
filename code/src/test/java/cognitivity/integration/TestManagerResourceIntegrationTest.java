package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.controllers.TestManagerController;
import cognitivity.entities.TestManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ophir on 19/01/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
public class TestManagerResourceIntegrationTest extends AbstractResourceIntegrationTest {

    // private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestManagerController controller;

    @Before
    public void setup() {
        super.setup();
    }

    private TestManager manager;

    public static long saveTestManager(TestManager manager, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        String contentAsString = mockMvc.perform(post("/test-managers/saveTestManager")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(manager)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return gson.fromJson(
                contentAsString,
                TestManager.class).getId();
    }

    public static void deleteTestManager(String managerId, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        mockMvc.perform(delete("/test-managers/deleteTestManager")
                .param("testManagerId", managerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveTestManager() throws Exception {
        manager = createTestManager(9L);
        // In order to save test, must save manager first.
        saveTestManager(manager, objectMapper, testManagerMvc);

        // Test is saved. Try find it in the database...
        String managerId = testManagerMvc.perform((get("/test-managers/findTestManagerIdByEmail"))
                .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        // Finally delete the test
        deleteTestManager(managerId, objectMapper, testManagerMvc);
    }
}
