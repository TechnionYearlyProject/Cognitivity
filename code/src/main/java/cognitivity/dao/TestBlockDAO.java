package cognitivity.dao;

import cognitivity.dto.BlockWrapper;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;

public interface TestBlockDAO {

    public TestBlock get(Long id);
    public void delete(Long id);
    public Long add(BlockWrapper data);
    public void update(BlockWrapper data);

    /**
     * Get all the questions for a given block.
     *
     * @param blockID - The Id of the given block.
     * @return - A list of all the questions in the block.
     */
    public List<TestQuestion> getAllBlockQuestions(long blockID);

}
