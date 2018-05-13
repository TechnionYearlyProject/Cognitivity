package cognitivity.services;

import cognitivity.dao.TestBlockDAO;
import cognitivity.dto.BlockWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service for test Blocks related operations.
 * @Author - Pe'er
 * @Date - 2.2.18
 */

@Service
public class TestBlockService {

    private final static Logger logger = Logger.getLogger(TestBlockService.class);

    private TestBlockDAO dao;

    @Autowired
    public TestBlockService(TestBlockDAO dao) {
        this.dao = dao;
    }

    /**
     * Build a test block, and add it to the DB.
     *
     * @param numberOfQuestions - the number of questions in the block.
     * @param randomize         - A boolean that holds true if the block is randomized
     * @param tag               - A tag attached to the block.
     * @param test              - The test the block corresponds to.
     * @return
     */
    public BlockWrapper createTestBlock(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test) throws DBException {
        try {
            BlockWrapper res = new BlockWrapper(numberOfQuestions, randomize, tag, test);
            dao.add(res.innerBlock(test.getId()));
            logger.info("Successfully added TestBlock. TestBlockID: " + res.getId());
            return res;
        } catch (org.hibernate.HibernateException e) {
            logger.info("Failed to add TestBlock.",e);
            throw new DBException(ErrorType.SAVE, test.getId());
        }
    }

    /**
     * Get a specific test Block by its Id
     *
     * @param Id - The Id of the test block
     * @return - The Test block with the given ID.
     */
    //TODO:Do we use this method? It doesn't work properly as it's currently written
    public void findBlockById(long Id) {
        List<TestQuestion> questions = dao.getAllBlockQuestions(Id);
    }

    /**
     * Update a block in the DB
     *
     * @param block - The block that needs to be updated.
     */
    //TODO:Do we need this method?
    public void updateTestBlock(TestBlock block)throws DBException {
        try{
            dao.update(block);
            logger.info("Successfully updated TestBlock. TestBlockID: " + block.getId());
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to update a TestBlock. TestBlockID: " + block.getId(),e);
            throw new DBException(ErrorType.UPDATE, block.getId());
        }
    }

    /**
     * Return all block questions for a specific block.
     * @param blockId - The Id of the block.
     * @return - A list of all the questions in the block.
     */
    public List<TestQuestion> findAllBlockQuestions(long blockId)throws DBException {
        try{
            List<TestQuestion> toReturn = dao.getAllBlockQuestions(blockId);
            logger.info("Successfully got all questions from a TestBlock. TestBlockID: " + blockId);
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to get all questions from a TestBlock. TestBlockID: " + blockId,e);
            throw new DBException(ErrorType.GET, blockId);
        }
    }

    /**
     * Delete a block from the DB.
     *
     * @param blockId - the block Id we want to delete.
     */
    public void deleteTestBlock(long blockId) throws DBException {
        try {
            dao.delete(blockId);
            logger.info("Successfully deleted TestBlock. TestBlockID: " + blockId);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to delete a TestBlock. TestBlockID: " + blockId,e);
            throw new DBException(ErrorType.DELETE, blockId);
        }
    }
}
