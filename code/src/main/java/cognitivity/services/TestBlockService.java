package cognitivity.services;

import cognitivity.dao.TestBlockDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test Blocks related operations.
 *
 */

@Service
public class TestBlockService {

    private TestBlockDAO dao;

    @Autowired
    public TestBlockService(TestBlockDAO dao) {
        this.dao = dao;
    }
    /**
     * Build a test block, and add it to the DB.
     *
     * @param numberOfQuestions - the number of questions in the block.
     * @param randomize - A boolean that holds true if the block is randomized
     * @param tag - A tag attached to the block.
     * @param test - The test the block corresponds to.
     * @return
     */
    public TestBlock createTestBlock(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test){
//        TestBlockDAOimpl dao = new TestBlockDAOimpl();
        TestBlock res = new TestBlock(numberOfQuestions,randomize,tag,test);
        dao.add(res);
        return res;
    }

    /**
     * Get a specific test Block by its Id
     * @param Id - The Id of the test block
     * @return - The Test block with the given ID.
     */
    public TestBlock findBlockById(long Id){
//        TestBlockDAOimpl dao = new TestBlockDAOimpl();
        return dao.get(Id);
    }

    /**
     * Update a block in the DB
     *
     * @param block - The block that needs to be updated.
     */
    public void updateTestBlock(TestBlock block){
//        TestBlockDAOimpl dao = new TestBlockDAOimpl();
        dao.update(block);
    }

    /**
     * Delete a block from the DB.
     *
     * @param blockId - the block Id we want to delete.
     */
    public void deleteTestBlock(long blockId){
//        TestBlockDAOimpl dao = new TestBlockDAOimpl();
        dao.delete(blockId);
    }
}
