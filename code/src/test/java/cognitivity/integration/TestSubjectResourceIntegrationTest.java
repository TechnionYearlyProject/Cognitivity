package cognitivity.integration;

import cognitivity.TestUtil;
import cognitivity.entities.TestSubject;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.IntegrationTestContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ophir on 20/01/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IntegrationTestContextConfiguration.class})
public class TestSubjectResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        super.setup();
    }

    private TestSubject subject;

    public static long saveTestSubject(TestSubject subject, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        return gson.fromJson(
                mockMvc.perform(post("/test-subjects/saveTestSubject")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(subject)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                TestSubject.class).getId();
    }

    public static void deleteTestSubject(String subjectId, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        mockMvc.perform(delete("/test-subjects/deleteTestSubject")
                .param("testSubjectId", subjectId))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveTestSubject() throws Exception {
        subject = createTestSubject(9L);
        // Save TestSubject...
        long subjectId = saveTestSubject(subject, objectMapper, testSubjectMvc);

        // Subject is saved. Try find it in the database...
        testSubjectMvc.perform((get("/test-subjects/findTestSubjectsForTestCriteria"))
                .param("testSubjectId", String.valueOf(subjectId))
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is("subject")))
                .andExpect(jsonPath("$[0].ipAddress", is("ip")))
                .andExpect(jsonPath("$[0].browser", is("browser")));

        // Finally delete the test...
        deleteTestSubject(String.valueOf(subjectId), objectMapper, testSubjectMvc);
    }

    @Test
    public void testUpdateTestSubject() throws Exception {
        subject = createTestSubject(9L);
        // Save TestSubject...
        long subjectId = saveTestSubject(subject, objectMapper, testSubjectMvc);
        subject.setId(subjectId);

        // Subject is saved. Try find it in the database...
        testSubjectMvc.perform((get("/test-subjects/findTestSubjectsForTestCriteria"))
                .param("testSubjectId", String.valueOf(subjectId))
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is("subject")))
                .andExpect(jsonPath("$[0].ipAddress", is("ip")))
                .andExpect(jsonPath("$[0].browser", is("browser")));

        subject.setName("subject2");
        subject.setIpAddress("ip2");
        subject.setBrowser("browser2");

        // Call to update...
        testSubjectMvc.perform(post("/test-subjects/updateTestSubject")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(subject)))
                .andExpect(status().isOk());

        // Check that it is updated...
        testSubjectMvc.perform((get("/test-subjects/findTestSubjectsForTestCriteria"))
                .param("testSubjectId", String.valueOf(subjectId))
                .param("testId", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].name", is("subject2")))
                .andExpect(jsonPath("$[0].ipAddress", is("ip2")))
                .andExpect(jsonPath("$[0].browser", is("browser2")));

        // Finally delete the test...
        deleteTestSubject(String.valueOf(subjectId), objectMapper, testSubjectMvc);
    }

}
