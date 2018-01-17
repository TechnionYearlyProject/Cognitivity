package cognitivity.dao;

import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;

public interface TestBlockDAO {

    public TestBlock get(Long id);
    public void delete(Long id);
    public void add(TestBlock data);
    public void update(TestBlock data);

    /**
     * Get all the questions for a given block.
     *
     * @param blockID - The Id of the given block.
     * @return - A list of all the questions in the block.
     */
    public List<TestQuestion> getAllBlockQuestions(long blockID);

}
