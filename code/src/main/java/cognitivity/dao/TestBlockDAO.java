package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestBlock;
import org.springframework.stereotype.Repository;

@Repository
public class TestBlockDAO extends AbstractDAO<TestBlock> {

    public TestBlock get(Long id){
        return super.get(id, TestBlock.class);
    }

    public void delete(Long id){
        super.delete(id, TestBlock.class);
    }
}
