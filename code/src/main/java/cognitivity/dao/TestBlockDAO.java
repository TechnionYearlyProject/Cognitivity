package cognitivity.dao;

import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;
/**
 * Created by Guy on 20/1/18.
 */
public interface TestBlockDAO {

    /**
     *
     * @Note! API documentation of get, delete, add and update is in the AbstractDAO
     *
     */
    public TestBlock get(Long id);
    public void delete(Long id);
    public long add(TestBlock data);
    public long update(TestBlock data);

    /**
     * Get all the questions for a given block.
     *
     * @param blockID - The Id of the given block.
     * @return - A list of all the questions in the block.
     */
    public List<TestQuestion> getAllBlockQuestions(long blockID);

}
