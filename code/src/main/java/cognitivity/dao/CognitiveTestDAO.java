package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;

public interface CognitiveTestDAO {

    public CognitiveTest get(Long id);
    public void delete(Long id);
    public void add(CognitiveTest data);
    public void update(CognitiveTest data);

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
     * Get all tests a given test manager has created.
     *
     * @param managerId - The manager Id whose tests we are looking for.
     *
     * @return - A list of all the managers tests.
     */
    public List<CognitiveTest> getCognitiveTestOfManager(long managerId);

}
