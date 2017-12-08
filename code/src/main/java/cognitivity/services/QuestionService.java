package cognitivity.services;

import cognitivity.dao.TestQuestionRepository;
import cognitivity.model.TestQuestion;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test questions related operations.
 *
 */

@Service
public class QuestionService extends AbstractService<TestQuestionRepository> {

    protected QuestionService(TestQuestionRepository repository) {
        super(repository);
    }

    public void addQuestion(TestQuestion q) {

    }

    public TestQuestion[] getQuestions() {
        return new TestQuestion[0];
    }

    public TestQuestion getQuestion(String id) {
        return null;
    }
}
