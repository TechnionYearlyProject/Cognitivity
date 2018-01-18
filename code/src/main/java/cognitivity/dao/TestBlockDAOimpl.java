package cognitivity.dao;

import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestBlockDAOimpl extends AbstractDAO<TestBlock> implements TestBlockDAO{

    public TestBlock get(Long id) { return super.get(id, TestBlock.class);}

    public void delete(Long id) {
        super.delete(id, TestBlock.class);
    }

    /**
     * Get all the questions for a given block.
     *
     * @param blockID - The Id of the given block.
     * @return - A list of all the questions in the block.
     */
    //TODO: Implement this function
    public List<TestQuestion> getAllBlockQuestions(long blockID){
        return null;
    }

}
