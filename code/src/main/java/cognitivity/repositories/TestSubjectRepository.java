package cognitivity.repositories;

import cognitivity.dao.TestSubject;
import org.springframework.stereotype.Repository;

/**
 *
 * Data Access Object representation of a repository of cognitive test's subjects.
 *
 * This class will use hibernate to pull and push data from the sql data server.
 * This data will be a representation of a cognitive test's subject.
 *
 */


@Repository
public class TestSubjectRepository extends AbstractRepository<TestSubject> {
}
