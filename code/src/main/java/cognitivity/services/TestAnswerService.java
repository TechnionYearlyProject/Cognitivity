package cognitivity.services;

import cognitivity.dao.TestAnswerRepository;
import cognitivity.dto.TestAnswerDTO;
import cognitivity.model.RepositorySearchResult;
import cognitivity.model.TestAnswer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Business service for answers to test questions related operations.
 *
 */
public class TestAnswerService extends AbstractService<TestAnswerRepository> {

    @Autowired
    public TestAnswerService(TestAnswerRepository repository) {
        super(repository);
    }

    /**
     * Add a TestAnswer for a test question.
     *
     * @param questionId - The test question the answer belongs to.
     * @param answerDTO - The test answer to be added.
     *
     * @return - The added TestAnswer.
     *
     * This will be used in conjunction with the POST HTTP method.
     * */
    public TestAnswer addTestAnswerForTestQuestion(String questionId, TestAnswerDTO answerDTO) {
        return null;
    }

    /**
     * Update a TestAnswer for a question.
     *
     * @param questionId - The question id.
     * @param answerId - The test answer to update.
     * @param answerDTO - The updated test answer to be written.
     *
     * @return - The updated TestAnswer.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public TestAnswer updateTestAnswerForQuestion(String questionId, String answerId, TestAnswerDTO answerDTO) {
        return null;
    }

    /**
     * Delete a TestAnswer for a question.
     *
     * @param questionId (optional - null if none) - The question id.
     * @param answerId - The test answer's id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestAnswerForQuestion(String questionId, String answerId) {

    }

    /**
     * Delete all answers for a question.
     *
     * @param questionId (optional - null if none) - The question id.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteAllTestAnswersForQuestion(String questionId) {

    }

    /**
     * Find test answer with given id.
     *
     * @param answerId - The test answer's id.
     *
     * @return - Test answer with given id.
     * */
    public TestAnswer findTestAnswerById(String answerId) {
        return null;
    }

    /**
     * Find all test answers that belong to a test question.
     *
     * @param questionId - The test question's id.
     *
     * @return - All test answers that belong to the question with the given id.
     * */
    public RepositorySearchResult<TestAnswer> findTestAnswersByQuestionId(String questionId) {
        return null;
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subject's id.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public RepositorySearchResult<TestAnswer> findTestAnswersBySubjectId(String subjectId) {
        return null;
    }

}
