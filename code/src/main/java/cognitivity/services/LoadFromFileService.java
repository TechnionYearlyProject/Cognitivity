package cognitivity.services;

import cognitivity.dao.*;
import cognitivity.exceptions.LoaderException;
import cognitivity.services.fileLoader.Test;
import cognitivity.services.fileLoader.TestReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

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
    public LoadFromFileService(TestQuestionDAO testQuestionDAO, TestAnswerDAO answerDAO, TestManagerDAO managerDAO,
                               CognitiveTestDAO testDAO, TestBlockDAO testBlockDAO) {
        this.testQuestionDAO = testQuestionDAO;
        this.answerDAO = answerDAO;
        this.managerDAO = managerDAO;
        this.testDAO = testDAO;
        this.testBlockDAO = testBlockDAO;
    }

    /**
     * The API for converting a json representation of a cognitive test, to data in the DB.
     *
     * @param jsonFileName - the file that contains the json test.
     */
    public void loadFromJSONFile(String jsonFileName) throws LoaderException {
        try {
            Test test = new TestReader(jsonFileName).read();
            logger.info("Successfully read Test Object from json file");
            // TODO : write to mysql
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + "when reading json file. ");
            throw new LoaderException(jsonFileName);
        }
    }

    /**
     * The API for converting a json representation of a cognitive test, to data in the DB.
     * Converts all files in directory to tests in DB.
     *
     * @param dirName - the directory that contains all the files.
     */
    public void loadTestFromDirectory(String dirName) throws LoaderException {
        File dir = new File(dirName);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isFile()) {
                    loadFromJSONFile(child.getAbsolutePath());
                }
            }
        } else {
            logger.error("Could not open directory to files");
            throw new LoaderException(dirName);
        }
    }
}
