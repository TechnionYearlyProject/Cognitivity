package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.exceptions.*;
import cognitivity.services.fileLoader.ITestReader;
import cognitivity.services.fileLoader.Test;
import cognitivity.services.fileLoader.TestDBSaver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * This class's goal is to provide the service for the frontend api of loading tests from json files.
 * <p>
 * Created by ophir on 12/05/18.
 */
@Service
public class LoadFromFileService {

    private final static Logger logger = Logger.getLogger(LoadFromFileService.class);
    private final Function<String, ITestReader> readerFactory;

    private TestQuestionDAO testQuestionDAO;
    private CognitiveTestDAO testDAO;
    private TestBlockDAO testBlockDAO;
    private TestManagerDAO testManagerDAO;


    @Autowired
    public LoadFromFileService(TestQuestionDAO testQuestionDAO,
                               CognitiveTestDAO testDAO,
                               TestBlockDAO testBlockDAO,
                               TestManagerDAO testManagerDAO,
                               Function<String, ITestReader> readerFactory) {
        this.testQuestionDAO = testQuestionDAO;
        this.testDAO = testDAO;
        this.testBlockDAO = testBlockDAO;
        this.readerFactory = readerFactory;
        this.testManagerDAO = testManagerDAO;
    }

    /**
     * The API for converting a json representation of a cognitive test, to data in the DB.
     *
     * @param jsonData - the file content that contains the json test.
     */
    public void loadFromJSONFile(String jsonData, long managerId) throws LoaderException, DBException {
        if (!testManagerDAO.managerWithIdExists(managerId)) {
            logger.warn("Manager with this id (" + managerId + ") does not exist in the DB");
            throw new ManagerDoesNotExistLoadException(jsonData);
        }
        long defaultId = 0;
        ITestReader reader = readerFactory.apply(jsonData);
        try {
            Test test = reader.read();
            if (testDAO.testWithNameExists(test.getName())) {
                logger.warn("Test with this name (" + test.getName() + ") already exists in the DB");
                throw new TestNameAlreadyExistsLoadException(jsonData);
            }
            logger.info("Successfully read Test Object from json file");
            TestDBSaver saver = new TestDBSaver(test, this.testDAO, testBlockDAO, testQuestionDAO, managerId)
                    .convert();
            defaultId = saver.getWrapper().getId();
            saver.writeToMySql();
            logger.info("Successfully written Test Object to Database");
        } catch (DBException e) {
            logger.error(e.getMessage() + " when trying to save test to database");
            throw new DBException(ErrorType.SAVE, defaultId);
        } catch (com.google.gson.JsonSyntaxException e) {
            logger.error(e.getMessage() + " when trying to parse test content");
            throw new JsonTestParseError(jsonData);
        }
    }
}
