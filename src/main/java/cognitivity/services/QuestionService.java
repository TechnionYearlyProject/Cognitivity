package cognitivity.services;

import cognitivity.model.TestQuestion;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test questions related operations.
 *
 */

@Service
public class QuestionService {

    public void addQuestion(TestQuestion q) {

    }

    public TestQuestion[] getQuestions() {
        return new TestQuestion[0];
    }

    public TestQuestion getQuestion(String id) {
        return null;
    }
}
