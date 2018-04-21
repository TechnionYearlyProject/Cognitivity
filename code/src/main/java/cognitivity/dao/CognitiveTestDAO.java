package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;

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
     * Method for searching for all cognitive tests of a manager without fetching the questions.
     *
     * @param managerId - id of the manager the request is build on.
     * @return - All tests that their manager has the id (param) without the questions (no wrapper)
     */
    public List<CognitiveTest> findTestsForTestManagerWithoutQuestions(long managerId);

}
