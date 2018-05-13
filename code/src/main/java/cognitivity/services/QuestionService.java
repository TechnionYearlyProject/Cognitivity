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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service for test questions related operations.
 * @Author - Pe'er
 * @Date - 2.2.18
 */

@Service
public class QuestionService {

    private final static Logger logger = Logger.getLogger(QuestionService.class);

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
            logger.info("Successfully added TestQuestion. TestQuestionId = " + q.getId());
            return q;
        } catch (org.hibernate.HibernateException e) {
            logger.error(e.getMessage());
            throw new DBException(ErrorType.SAVE, q.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            logger.info("Successfully updated TestQuestion. TestQuestionId = " + q.getId());
        } catch (org.hibernate.HibernateException e) {
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, q.getId());
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
            logger.info("Successfully deleted TestQuestion. TestQuestionId = " + questionId);
        } catch (org.hibernate.HibernateException e) {
            logger.error(e.getMessage());
            throw new DBException(ErrorType.DELETE, questionId);
        }
    }

    /**
     * find a specific question by its id.
     *
     * @param id - the question id.
     * @return the question corresponding to the given id if it exists, null otherwise
     */
    public TestQuestion findQuestionById(long id)throws DBException {
        try{
            return dao.get(id);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, id);
        }
    }

    /**
     * Get all answers to a given question
     *
     * @param questionId - The test question we want to get the answer to.
     * @return - All the answers to the given question.
     */
    public List<TestAnswer> getTestAnswers(long questionId)throws DBException {
        try {
            return answerDao.getQuestionAnswers(questionId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, questionId);
        }
    }

    /**
     * Get all test questions from a given test.
     *
     * @param testId - The test Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromTestId(long testId)throws DBException {
        try{
            return testDao.getTestQuestions(testId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, testId);
        }
    }

    /**
     * Get all test questions from a given manager.
     *
     * @param managerId - The manager Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromManagerId(long managerId)throws DBException {
        try{
            TestManager testManager = managerDao.get(managerId);
            return dao.getTestQuestionsFromAManager(testManager);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, managerId);
        }
    }

    /**
     * Method for getting a picture link from a specific question
     *
     * @param questionId - id of the question whose picture we are looking for
     * @return - The link to the picture.
     */
    public String findPictureLinkPerQuestion(long questionId) throws DBException{
        try {
            return dao.findPictureLinkPerQuestion(questionId);
        } catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, questionId);
        }
    }

}
