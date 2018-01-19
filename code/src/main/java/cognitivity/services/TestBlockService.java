package cognitivity.services;

import cognitivity.dao.TestBlockDAO;
import cognitivity.dto.BlockWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public BlockWrapper createTestBlock(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test){
        BlockWrapper res = new BlockWrapper(numberOfQuestions, randomize, tag, test);
        dao.add(res.innerBlock());
        return res;
    }

    /**
     * Get a specific test Block by its Id
     * @param Id - The Id of the test block
     * @return - The Test block with the given ID.
     */
    public void findBlockById(long Id){
        List<TestQuestion> questions = dao.getAllBlockQuestions(Id);
    }

    /**
     * Update a block in the DB
     *
     * @param block - The block that needs to be updated.
     */
    public void updateTestBlock(TestBlock block){
        // dao.update(block);
    }

    /**
     *
     * @param blockId
     * @return
     */
    public List<TestQuestion> findAllBlockQuestions(long blockId){
        return dao.getAllBlockQuestions(blockId);
    }

    /**
     * Delete a block from the DB.
     *
     * @param blockId - the block Id we want to delete.
     */
    public void deleteTestBlock(long blockId){
        dao.delete(blockId);
    }
}
