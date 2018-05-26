package cognitivity.controllers;

import cognitivity.entities.TestSubject;
import cognitivity.exceptions.SendLinksException;
import cognitivity.services.DistributeTestLinkToSubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.TestContextBeanConfiguration;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ophir on 26/5/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        TestContextBeanConfiguration.class
})
public class SendLinkControllerTest implements RestControllerTest {

    private DistributeTestLinkToSubjectController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DistributeTestLinkToSubjectService distributeTestLinkToSubjectService;

    @Before
    public void setUp() {
        Mockito.reset(distributeTestLinkToSubjectService);

        controller = new DistributeTestLinkToSubjectController(distributeTestLinkToSubjectService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }

    @Test
    public void testThatServiceMethodIsDelegated() throws SendLinksException, Exception {
        List<String> subjectList = Collections.singletonList("email@gmail.com");

        mockMvc.perform(post("/send-links/sendLinksToSubjects")
                .param("link", "link")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(subjectList)))
                .andExpect(status().isOk());

        Mockito.verify(distributeTestLinkToSubjectService, times(1)).sendLinksToSubjects(subjectList, "link");
        Mockito.verifyNoMoreInteractions(distributeTestLinkToSubjectService);
    }

    @Test
    @Ignore
    public void testSendLinks() throws SendLinksException {
        TestSubject subject = new TestSubject(
                "Ophir",
                "ip",
                "browser",
                "s",
                "oc",
                "single as fuck",
                "ophirk8396@gmail.com"
        );


        // testSubjectDAO.add(subject);
        distributeTestLinkToSubjectService.sendLinksToSubjects(Collections.singletonList(subject.getEmail()), "localhost:4200/test/8");
    }
}
