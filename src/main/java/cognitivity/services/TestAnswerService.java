package cognitivity.services;

import cognitivity.model.TestAnswer;

/**
 *
 * Business service for answers to test questions related operations.
 *
 */
public class TestAnswerService {

    public void addTestAnswers(String testID, String subjectID, TestAnswer[] testAnswers) {

    }

    public TestAnswer[] getTestAnswers(String testID, String subjectID) {
        return new TestAnswer[0];
    }

    public TestAnswer getTestAnswer(String testID, String subjectID, String answerID) {
        return null;
    }
}
