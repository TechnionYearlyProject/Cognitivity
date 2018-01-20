package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestAnswerDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service for test questions related operations.
 */

@Service
public class QuestionService {
    private TestQuestionDAO dao;
    private TestAnswerDAO answerDao;
    private CognitiveTestDAO testDao;
    private TestManagerDAO managerDao;

    @Autowired
    public QuestionService(TestQuestionDAO dao, TestAnswerDAO answerDao, CognitiveTestDAO testDao, TestManagerDAO managerDao) {
        this.dao = dao;
        this.answerDao = answerDao;
        this.testDao = testDao;
        this.managerDao = managerDao;
    }

    /**
     * Save a TestQuestion.
     *
     * @param q - the question to be saved
     * @return - The saved TestQuestion.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestQuestion createTestQuestion(TestQuestion q) throws DBException {
        try {
            dao.add(q);
            return q;
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.SAVE);
        }
    }

    /**
     * Update a TestQuestion.
     *
     * @param q - The cognitive test question to be updated.
     *          <p>
     *          This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestQuestion(TestQuestion q) throws DBException {
        try {
            dao.update(q);
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.UPDATE);
        }
    }


    /**
     * Delete a TestQuestion.
     *
     * @param questionId - The test question Id to delete.
     *                   <p>
     *                   Important Note: This will delete all answers associated with the question! (maybe)
     *                   <p>
     *                   This will be used in conjunction with the DELETE HTTP method.
     */
    //TODO: do we want to delete all corresponding test answers as well?
    public void deleteTestQuestion(long questionId) throws DBException {
        try {
            dao.delete(questionId);
        } catch (org.hibernate.HibernateException e) {
            throw new DBException(ErrorType.DELETE);
        }
    }

    /**
     * find a specific question by its id.
     *
     * @param id - the question id.
     * @return the question corresponding to the given id if it exists, null otherwise
     */
    public TestQuestion findQuestionById(long id) {
        return dao.get(id);
    }

    /**
     * Get all answers to a given question
     *
     * @param questionId - The test question we want to get the answer to.
     * @return - All the answers to the given question.
     */
    public List<TestAnswer> getTestAnswers(long questionId) {
        return answerDao.getTestAnswers(questionId);
    }

    /**
     * Get all test questions from a given test.
     *
     * @param testId - The test Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromTestId(long testId) {
        return testDao.getTestQuestions(testId);
    }

    /**
     * Get all test questions from a given manager.
     *
     * @param managerId - The manager Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromManagerId(long managerId) {
        TestManager testManager = managerDao.get(managerId);
        return dao.getTestQuestionsFromAManager(testManager);
    }

    public TestQuestion findTestQuestionById(long testQuestionId) {
        return dao.get(testQuestionId);
    }
}
