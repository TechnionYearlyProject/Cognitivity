package cognitivity.dao;

import cognitivity.entities.TestManager;

public interface TestManagerDAO {

    public TestManager get(Long id);
    public void delete(Long id);
    public void add(TestManager data);
    public void update(TestManager data);

}
