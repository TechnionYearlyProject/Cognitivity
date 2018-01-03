package cognitivity.services;

import cognitivity.dao.TestBlockDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test Blocks related operations.
 *
 */

@Service
public class TestBlockService {

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
        TestBlockDAO dao = new TestBlockDAO();
        TestBlock res = new TestBlock(numberOfQuestions,randomize,tag,test);
        dao.add(res);
        return res;
    }

    /**
     * Update a block in the DB
     *
     * @param block - The block that needs to be updated.
     */
    public void updateTestBlock(TestBlock block){
        TestBlockDAO dao = new TestBlockDAO();
        dao.update(block);
    }

    /**
     * Delete a block from the DB.
     *
     * @param blockId - the block Id we want to delete.
     */
    public void deleteTestBlock(long blockId){
        TestBlockDAO dao = new TestBlockDAO();
        dao.delete(blockId);
    }
}
