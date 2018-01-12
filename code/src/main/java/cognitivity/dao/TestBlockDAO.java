package cognitivity.dao;

import cognitivity.entities.TestBlock;

public interface TestBlockDAO {

    public TestBlock get(Long id);
    public void delete(Long id);
    public void add(TestBlock data);
    public void update(TestBlock data);

}
