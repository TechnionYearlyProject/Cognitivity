package cognitivity.dao;

import cognitivity.entities.TestManager;

public interface TestManagerDAO {

    public TestManager get(Long id);
    public void delete(Long id);

    public long add(TestManager data);
    public long update(TestManager data);
    public long getId(String email);



}
