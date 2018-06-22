package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.exceptions.*;
import cognitivity.services.fileLoader.TestReader;
import config.LoadFromFileDependencyBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static cognitivity.TestUtil.jsonData;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.anyLong;

/**
 * Created by ophir on 25/05/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        LoadFromFileDependencyBeanConfiguration.class
})
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

        exception.expect(ManagerDoesNotExistLoadException.class);
        exception.expectMessage(ManagerDoesNotExistLoadException.errorMessage);
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    public void testNameExists_ShouldWarnAndThrowLoaderException() throws DBException, LoaderException {
        service = new LoadFromFileService(
                testQuestionDAO,
                cognitiveTestDAO,
                testBlockDAO,
                testManagerDAO,
                (s) -> () -> new cognitivity.services.fileLoader.Test(
                        null,
                        "test",
                        null,
                        null,
                        null
                )
        );
        Mockito.when(testManagerDAO.managerWithIdExists(anyLong()))
                .thenReturn(true);
        Mockito.when(cognitiveTestDAO.testWithNameExists("test"))
                .thenReturn(true);

        exception.expect(TestNameAlreadyExistsLoadException.class);
        exception.expectMessage(TestNameAlreadyExistsLoadException.errorMessage);
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    public void testFileContentCorrupted_ShouldThrowLoaderException() throws DBException, LoaderException {
        Mockito.when(testManagerDAO.managerWithIdExists(anyLong()))
                .thenReturn(true);
        Mockito.when(cognitiveTestDAO.testWithNameExists("test"))
                .thenReturn(false);

        exception.expect(JsonTestParseError.class);
        exception.expectMessage(JsonTestParseError.errorMessage);
        service.loadFromJSONFile("data", 1L);
    }

    @Test
    @Ignore
    public void testFileContentOkay_ShouldFinishLoading() {
        try {
            Mockito.when(testManagerDAO.managerWithIdExists(anyLong()))
                    .thenReturn(true);
            Mockito.when(cognitiveTestDAO.testWithNameExists(anyString()))
                    .thenReturn(false);

            service = new LoadFromFileService(
                    testQuestionDAO,
                    cognitiveTestDAO,
                    testBlockDAO,
                    testManagerDAO,
                    (s) -> new TestReader(jsonData)
            );


            service.loadFromJSONFile(jsonData, 1L);
        } catch (Exception e) {
            fail();
        }

    }


}
