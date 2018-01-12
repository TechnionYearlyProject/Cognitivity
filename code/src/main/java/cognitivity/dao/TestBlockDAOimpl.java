package cognitivity.dao;

import cognitivity.entities.TestBlock;
import org.springframework.stereotype.Repository;

@Repository
public class TestBlockDAOimpl extends AbstractDAO<TestBlock> implements TestBlockDAO{

    public TestBlock get(Long id) { return super.get(id, TestBlock.class);}

    public void delete(Long id) {
        super.delete(id, TestBlock.class);
    }

}
