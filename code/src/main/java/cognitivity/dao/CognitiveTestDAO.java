package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;
/**
 * Created by Guy on 20/1/18.
 */
public interface CognitiveTestDAO {

    /**
     *
     * @Note! API documentation of get, delete, add and update is in the AbstractDAO
     *
     */
    public CognitiveTest get(Long id);
    public void delete(Long id);
    public long add(CognitiveTest data);
    public long update(CognitiveTest data);

    /**
     *
     * Inserts CognitiveTest without fetching the TestManager from the database
     *
     * @param cognitiveTest the CognitiveTest data without the testManager
     * @param testManagerId the id of the TestManager of the test
     * @return the allocated id of the new added CognitiveTest
     *
     */
    public long add(CognitiveTest cognitiveTest, Long testManagerId);

    /**
     * Return all questions in a given test
     *
     * @param testId - The cognitive test to which we want to get all questions.
     *
     * @return - A list containing all the test questions in the test.
     */
    public List<TestQuestion> getTestQuestions(long testId);

    /**
     * Return all blocks in a given test.
     *
     * @param testId - The cognitive test to which we want to get all blocks.
     *
     * @return - A list containing all the test blocks in the test.
     */
    public List<TestBlock> getTestBlocks(long testId);

    /**
     * Returns all the tests that a given test manager has created.
     *
     * @param managerId - The manager Id whose tests we are looking for.
     *
     * @return - A list of all the managers tests.
     */
    public List<CognitiveTest> getCognitiveTestOfManager(long managerId);

    /**
     * Returns all the tests that have certain substring in the notes
     * @param notesFilter - The substring to search
     * @return - A list of all cognitive tests by the notes
     */
    public List<CognitiveTest> filterTestsByNotes(String notesFilter);

    /**
     * Returns all the tests that have certain substring in the projectFilter
     * @param projectFilter - The substring to search
     * @return - A list of all cognitive tests by the projectFilter
     */
    public List<CognitiveTest> filterTestsByProject(String projectFilter);

    /**
     * Check if a test exists with a specified name
     */
    boolean testWithNameExists(String name);

}
