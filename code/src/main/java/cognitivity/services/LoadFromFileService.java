package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import cognitivity.exceptions.LoaderException;
import cognitivity.services.fileLoader.Test;
import cognitivity.services.fileLoader.TestDBSaver;
import cognitivity.services.fileLoader.TestReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * This class's goal is to provide the service for the frontend api of loading tests from json files.
 * <p>
 * Created by ophir on 12/05/18.
 */
@Service
public class LoadFromFileService {

    private final static Logger logger = Logger.getLogger(LoadFromFileService.class);

    private TestQuestionDAO testQuestionDAO;
    private TestAnswerDAO answerDAO;
    private TestManagerDAO managerDAO;
    private CognitiveTestDAO testDAO;
    private TestBlockDAO testBlockDAO;


    @Autowired
    public LoadFromFileService(TestQuestionDAO testQuestionDAO,
                               TestAnswerDAO answerDAO,
                               TestManagerDAO managerDAO,
                               CognitiveTestDAO testDAO,
                               TestBlockDAO testBlockDAO) {
        this.testQuestionDAO = testQuestionDAO;
        this.answerDAO = answerDAO;
        this.managerDAO = managerDAO;
        this.testDAO = testDAO;
        this.testBlockDAO = testBlockDAO;
    }

    /**
     * The API for converting a json representation of a cognitive test, to data in the DB.
     *
     * @param jsonData - the file content that contains the json test.
     */
    public void loadFromJSONFile(String jsonData, long managerId) throws LoaderException, DBException {
        long defaultId = 0;
        TestReader reader = new TestReader(jsonData);
        try {
            Test test = reader.read();
            if (testDAO.testWithNameExists(test.getName())) {
                logger.warn("Test with this name already exists in the DB");
                throw new LoaderException("Test with this name already exists in the DB");
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
        } catch (Exception e) {
            logger.error(e.getCause().getMessage() + " when trying to parse the json file. ");
            throw new LoaderException(jsonData);
        }
    }

    /**
     * The API for converting a json representation of a cognitive test, to data in the DB.
     * Converts all files in directory to tests in DB.
     *
     * @param dirName   - the directory that contains all the files.
     * @param managerId
     */
    public void loadTestFromDirectory(String dirName, long managerId) throws LoaderException, DBException {
        File dir = new File(dirName);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isFile()) {
                    loadFromJSONFile(child.getAbsolutePath(), managerId);
                }
            }
        } else {
            logger.error("Could not open directory to files");
            throw new LoaderException(dirName);
        }
    }
}
