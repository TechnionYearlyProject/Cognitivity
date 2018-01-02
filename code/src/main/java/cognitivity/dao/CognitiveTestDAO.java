package cognitivity.dao;

import cognitivity.entities.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Data Access Object
 */
@Repository
public class CognitiveTestDAO extends AbstractDAO<CognitiveTest> {

    public CognitiveTest get(Long id){
        return super.get(id, CognitiveTest.class);
    }

    public void delete(Long id){
        super.delete(id, CognitiveTest.class);
    }

    /**
     * Return all questions in a given test
     *
     * @param test - The cognitive test to which we want to get all questions.
     *
     * @return- A list containing all the test questions in the test.
     */
    public List<TestQuestion> getAllRelevantTestQuestions(CognitiveTest test){
        return null;
    }

    /**
     * Return all blocks in a given test.
     *
     * @param test - The cognitive test to which we want to get all blocks.
     *
     * @return- A list containing all the test blocks in the test.
     */
    public List<TestBlock> getAllRelevantTestBlocks(CognitiveTest test){
        return null;
    }

    /**
     * Get all tests a given test manager has created.
     *
     * @param manager - The manager whose tests we are looking for.
     *
     * @return - A list of all the managers tests.
     */
    public List<CognitiveTest> getAllCognitiveTestOfManager(TestManager manager){
        return null;
    }
}
