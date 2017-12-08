package cognitivity.services;

import cognitivity.dao.TestManagerRepository;
import cognitivity.model.TestManager;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test manager related operations.
 *
 */

@Service
public class TestManagerService extends AbstractService<TestManagerRepository> {


    protected TestManagerService(TestManagerRepository repository) {
        super(repository);
    }

    public void addTestManager(TestManager tm) {

    }

    public TestManager[] getTestManagers() {
        return new TestManager[0];
    }

    public TestManager getTestManager(String id) {
        return null;
    }
}
