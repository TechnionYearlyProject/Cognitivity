package cognitivity.controllers;

import cognitivity.services.LoadFromFileService;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ophir on 12/05/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class})
public class LoadFromFileControllerTest implements RestControllerTest {

    private LoadFromFileController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoadFromFileService loadFromFileServiceMock;

    @Before
    public void setUp() {
        Mockito.reset(loadFromFileServiceMock);

        controller = new LoadFromFileController(loadFromFileServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @Override
    public void controllerInitializedCorrectly() {
        Assert.assertThat(controller, CoreMatchers.notNullValue());
    }


    @Test
    public void testReadJSONFile() throws Exception {
        final String fileName = System.getProperty("user.dir") + "/src/test/resources/test1.json";

        Mockito.doNothing().when(loadFromFileServiceMock).loadFromJSONFile(fileName, 0);

        mockMvc.perform(post("/load-from-file/loadFromJSONFile")
                .param("fileName", fileName)
                .param("managerId", "0"))
                .andExpect(status().isOk());

        Mockito.verify(loadFromFileServiceMock, times(1)).loadFromJSONFile(fileName, 0);
        Mockito.verifyNoMoreInteractions(loadFromFileServiceMock);
    }

    @Test
    public void testReadJSONFromDir() throws Exception {
        final String dirName = System.getProperty("user.dir") + "/src/test/resources/";

        Mockito.doNothing().when(loadFromFileServiceMock).loadTestFromDirectory(dirName, 0);

        mockMvc.perform(post("/load-from-file/loadTestFromDirectory")
                .param("dirName", dirName)
                .param("managerId", "0"))
                .andExpect(status().isOk());

        Mockito.verify(loadFromFileServiceMock, times(1)).loadTestFromDirectory(dirName, 0);
        Mockito.verifyNoMoreInteractions(loadFromFileServiceMock);
    }
}


