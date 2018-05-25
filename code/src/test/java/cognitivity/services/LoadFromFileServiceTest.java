package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.LoaderException;
import cognitivity.services.fileLoader.TestReader;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import com.google.gson.JsonParser;
import config.LoadFromFileDependencyBeanConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by ophir on 25/05/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LoadFromFileDependencyBeanConfiguration.class, HibernateBeanConfiguration.class})
public class LoadFromFileServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private TestQuestionDAO testQuestionDAO;

    @Autowired
    private CognitiveTestDAO cognitiveTestDAO;

    @Autowired
    private TestBlockDAO testBlockDAO;

    @Autowired
    private LoadFromFileService service;

    @Autowired
    private TestManagerDAO testManagerDAO;

    @Before
    public void setup() {
        Mockito.reset(testQuestionDAO);
        Mockito.reset(cognitiveTestDAO);
        Mockito.reset(testBlockDAO);
        Mockito.reset(testManagerDAO);
    }

    @Test
    public void testManagerDoesNotExist_ShouldThrowLoaderException() throws DBException, LoaderException {
        Mockito.when(testManagerDAO.managerWithIdExists(1L))
                .thenReturn(false);

        exception.expect(LoaderException.class);
        exception.expectMessage("Manager with this id (1) does not exist in the DB");
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    public void testNameExists_ShouldWarnAndThrowLoaderException() throws DBException, LoaderException {
        service = new LoadFromFileService(
                testQuestionDAO,
                cognitiveTestDAO,
                testBlockDAO,
                testManagerDAO,
                () -> () -> new cognitivity.services.fileLoader.Test(
                        null,
                        "test",
                        null,
                        null,
                        null
                )
        );
        Mockito.when(testManagerDAO.managerWithIdExists(Matchers.anyLong()))
                .thenReturn(true);
        Mockito.when(cognitiveTestDAO.testWithNameExists("test"))
                .thenReturn(true);

        exception.expect(LoaderException.class);
        exception.expectMessage("Test with this name (test) already exists in the DB");
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    public void testFileContentCorrupted_ShouldThrowLoaderException() throws DBException, LoaderException {
        Mockito.when(testManagerDAO.managerWithIdExists(Matchers.anyLong()))
                .thenReturn(true);
        Mockito.when(cognitiveTestDAO.testWithNameExists("test"))
                .thenReturn(false);

        exception.expect(LoaderException.class);
        exception.expectMessage("Failed parsing test content");
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    public void testFileContentOkay_ShouldFinishLoading() throws DBException, LoaderException, FileNotFoundException {
        String jsonDataPath = System.getProperty("user.dir") + "/src/test/resources/test1.json";
        String jsonData = new JsonParser().parse(new FileReader(jsonDataPath)).toString();

        service = new LoadFromFileService(
                testQuestionDAO,
                cognitiveTestDAO,
                testBlockDAO,
                testManagerDAO,
                () -> new TestReader(jsonData)
        );

        Mockito.when(testManagerDAO.managerWithIdExists(Matchers.anyLong()))
                .thenReturn(true);
        Mockito.when(cognitiveTestDAO.testWithNameExists("test"))
                .thenReturn(false);

        service.loadFromJSONFile(jsonData, 1L);

    }


}
