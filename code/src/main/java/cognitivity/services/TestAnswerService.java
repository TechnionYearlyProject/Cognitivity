package cognitivity.services;

import cognitivity.dao.TestAnswerDAO;
import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.TestAnswer;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business service for answers to test questions related operations.
 * @Author - Pe'er
 * @Date - 2.2.18
 */
@Service
public class TestAnswerService {

    private final static Logger logger = Logger.getLogger(TestAnswerService.class);

    private TestAnswerDAO dao;
    private TestSubjectDAO subjectDao;

    @Autowired
    public TestAnswerService(TestAnswerDAO dao, TestSubjectDAO subjectDao) {
        this.dao = dao;
        this.subjectDao = subjectDao;
    }

    /**
     * Create a test answer, and add it to the DB.
     *
     * @param answer - the test answer being saved
     * @return - The created test answer.
     */
    public TestAnswer addTestAnswerForTestQuestion(TestAnswer answer) throws DBException {
        try {
            dao.add(answer);
            logger.info("Successfully added TestAnswer. TestAnswerId = " + answer.getId());
            return answer;
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.SAVE, answer.getId());
        }
    }

    /**
     * Update a TestAnswer for a question.
     *
     * @param answer - The test answer to update.
     *               <p>
     *               This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestAnswerForQuestion(TestAnswer answer) throws DBException {
        try {
            dao.update(answer);
            logger.info("Successfully updated TestAnswer. TestAnswerId = " + answer.getId());
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, answer.getId());
        }
    }

    /**
     * Delete a TestAnswer for a question.
     *
     * @param id - The test answer id to delete.
     *           <p>
     *           This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestAnswerForQuestion(long id) throws DBException {
        try {
            dao.delete(id);
            logger.info("Successfully deleted TestAnswer. TestAnswerId = " + id);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, id);
        }
    }

    /**
     * Delete all answers for a question.
     *
     * @param questionId - the question Id whose answers we want to delete.
     *                   <p>
     *                   This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteAllTestAnswersForQuestion(long questionId) throws DBException {
        try {
            List<TestAnswer> answers = dao.getQuestionAnswers(questionId);
            for (TestAnswer answer : answers) {
                dao.delete(answer.getId());
            }
            logger.info("Successfully deleted all test answers for TestQuestionId = "
                    + questionId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, questionId);
        }
    }

    /**
     * Find test answer with given id.
     *
     * @param answerId - The test answer's id.
     * @return - Test answer with given id, null if it doesn't exist.
     */
    public TestAnswer findTestAnswerById(long answerId)throws DBException {
        try{
            return dao.get(answerId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, answerId);
        }
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subjects Id.
     * @return - All test answers that belong to the subject with the given id.
     */
    public List<TestAnswer> findTestAnswersBySubject(long subjectId)throws DBException {
        try{
            return subjectDao.getSubjectAnswers(subjectId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, subjectId);
        }
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subject Id.
     * @param testId    - The test Id from which we want to get the results.
     * @return - All test answers that belong to the subject with the given id.
     */
    public List<TestAnswer> findTestAnswersBySubjectInTest(long subjectId, long testId) throws DBException{
        try{
            return dao.getTestSubjectAnswersInTest(subjectId, testId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, testId);
        }
    }

    /**
     * Get all the answers for a given test question.
     *
     * @param questionId - The question Id whose answers we are looking for.'
     * @return - A list of all the answers for the question.
     */
    public List<TestAnswer> findAllTestAnswerForAQuestion(long questionId)throws DBException {
        try{
            return dao.getQuestionAnswers(questionId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, questionId);
        }
    }
}
