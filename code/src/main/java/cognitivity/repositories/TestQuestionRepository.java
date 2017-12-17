package cognitivity.repositories;


import cognitivity.dao.TestQuestion;
import org.springframework.stereotype.Repository;

/**
 *
 * Data Access Object representation of a repository of cognitive test's questions.
 *
 * This class will use hibernate to pull and push data from the sql data server.
 * This data will be a representation of a cognitive test's question.
 *
 */

@Repository
public class TestQuestionRepository extends AbstractRepository<TestQuestion> {
}
