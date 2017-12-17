package cognitivity.services;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.dao.TestAnswer;
import cognitivity.dto.TestAnswerDTO;
import cognitivity.repositories.TestAnswerRepository;
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
    public TestAnswer addTestAnswerForTestQuestion(long questionId, TestAnswerDTO answerDTO) {
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
    public TestAnswer updateTestAnswerForQuestion(long questionId, long answerId, TestAnswerDTO answerDTO) {
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
    public void deleteTestAnswerForQuestion(long questionId, long answerId) {

    }

    /**
     * Delete all answers for a question.
     *
     * @param questionId (optional - null if none) - The question id.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteAllTestAnswersForQuestion(long questionId) {

    }

    /**
     * Find test answer with given id.
     *
     * @param answerId - The test answer's id.
     *
     * @return - Test answer with given id.
     * */
    public TestAnswer findTestAnswerById(long answerId) {
        return null;
    }

    /**
     * Find all test answers that belong to a test question.
     *
     * @param questionId - The test question's id.
     *
     * @return - All test answers that belong to the question with the given id.
     * */
    public RepositorySearchResult<TestAnswer> findTestAnswersByQuestionId(long questionId) {
        return null;
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subject's id.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public RepositorySearchResult<TestAnswer> findTestAnswersBySubjectId(long subjectId) {
        return null;
    }

}
