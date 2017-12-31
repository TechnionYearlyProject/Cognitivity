package cognitivity.services;

import cognitivity.entities.CognitiveTest;
import cognitivity.dao.RepositorySearchResult;
import cognitivity.entities.TestQuestion;
import cognitivity.dao.TestQuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test questions related operations.
 *
 */

@Service
public class QuestionService extends AbstractService<TestQuestion> {

    @Autowired
    protected QuestionService(TestQuestion question) {
        super(question);
    }

    /**
     * Save a TestQuestion.
     *
     * @param q - The cognitive test question to be created.
     *
     * @return - The saved TestQuestion.
     *
     * This will be used in conjunction with the POST HTTP method.
     * */
    public TestQuestion createTestQuestion(TestQuestionDAO q) {
        return null;
    }

    /**
     * Update a TestQuestion.
     *
     * @param questionId - The test question id to update.
     * @param q - The cognitive test question to be updated.
     *
     * @return - The updated TestQuestion.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public CognitiveTest updateTestQuestion(long questionId, TestQuestionDAO q) {
        return null;
    }

    /**
     * Delete a TestQuestion.
     *
     * @param questionId - The test question id to delete.
     *
     * Important Note: This will delete all answers associated with the question! (maybe)
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestQuestion(long questionId) {

    }


    /**
     * Find all cognitive test questions in a test.
     *
     * @param testId - The test's id.
     *
     * @return - All questions the test has.
     * */
    public RepositorySearchResult<TestQuestion> getTestQuestionsForTest(long testId) {
        return null;
    }

    /**
     * Find all cognitive test questions that a test manager has created.
     *
     * @param testManagerID - The test managers's id.
     *
     * @return - All questions the test manager has created.
     * */
    public RepositorySearchResult<TestQuestion> findTestQuestionsForTestManagerById(long testManagerID) {
        return null;
    }

}
