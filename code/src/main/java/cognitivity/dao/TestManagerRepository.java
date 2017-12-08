package cognitivity.dao;

import cognitivity.model.TestManager;
import org.springframework.stereotype.Repository;

/**
 *
 * Data Access Object representation of a repository of cognitive test's managers.
 *
 * This class will use hibernate to pull and push data from the sql data server.
 * This data will be a representation of a cognitive test's manager.
 *
 */


@Repository
public class TestManagerRepository extends AbstractRepository<TestManager> {
}
