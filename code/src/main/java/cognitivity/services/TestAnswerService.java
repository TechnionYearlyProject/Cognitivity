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
 *
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
            logger.info("Successfully added TestAnswer. TestAnswerId: " + answer.getId());
            return answer;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to add testAnswer. TestAnswerID: " + answer.getId(), e);
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
            logger.info("Successfully updated TestAnswer. TestAnswerID: " + answer.getId());
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to update testAnswer. TestAnswerID: " + answer.getId(), e);
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
            logger.info("Successfully deleted TestAnswer. TestAnswerID: " + id);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to delete testAnswer. TestAnswerID: " + id, e);
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
            logger.info("Successfully deleted all test answers for TestQuestionID: "
                    + questionId);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to delete all test answers for TestQuestionID: " + questionId, e);
            throw new DBException(ErrorType.UPDATE, questionId);
        }
    }

    /**
     * Find test answer with given id.
     *
     * @param answerId - The test answer's id.
     * @return - Test answer with given id, null if it doesn't exist.
     */
    public TestAnswer findTestAnswerById(long answerId) throws DBException {
        try {
            TestAnswer toReturn = dao.get(answerId);
            if (toReturn == null) {
                logger.error("Failed to find a test answers by ID. TestAnswer with ID: " + answerId + " doesn't exist");
                throw new DBException(ErrorType.DOESNT_EXIST, answerId);
            }
            logger.info("Successfully found a test answers by ID. TestAnswer ID: "
                    + answerId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find a test answers by ID. TestAnswer ID: " + answerId, e);
            throw new DBException(ErrorType.GET, answerId);
        }
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subjects Id.
     * @return - All test answers that belong to the subject with the given id.
     */
    public List<TestAnswer> findTestAnswersBySubject(long subjectId) throws DBException {
        try {
            if (subjectDao.get(subjectId) == null) {
                logger.error("Failed to find test answers by subject. Test subject with ID: " + subjectId + " doesn't exist");
                throw new DBException(ErrorType.DOESNT_EXIST, subjectId);
            }
            List<TestAnswer> toReturn = subjectDao.getSubjectAnswers(subjectId);
            logger.info("Successfully found test answers by subject. Test subject ID: "
                    + subjectId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find test answers by subject. Test subject ID: " + subjectId, e);
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
    public List<TestAnswer> findTestAnswersBySubjectInTest(long subjectId, long testId) throws DBException {
        try {
            if (subjectDao.get(subjectId) == null) {
                logger.error("Failed to find test answers by subject in test. Test subject with ID: "
                        + subjectId + " doesn't exist");
                throw new DBException(ErrorType.DOESNT_EXIST, subjectId);
            }
            List<TestAnswer> toReturn = dao.getTestSubjectAnswersInTest(subjectId, testId);
            logger.info("Successfully found test answers by subject in test. Test subject ID: "
                    + subjectId + ". Test ID: " + testId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find test answers by subject in test. Test subject ID: "
                    + subjectId + ". Test ID: " + testId, e);
            throw new DBException(ErrorType.GET, testId);
        }
    }

    /**
     * Get all the answers for a given test question.
     *
     * @param questionId - The question Id whose answers we are looking for.'
     * @return - A list of all the answers for the question.
     */
    public List<TestAnswer> findAllTestAnswerForAQuestion(long questionId) throws DBException {
        try {
            List<TestAnswer> toReturn = dao.getQuestionAnswers(questionId);
            logger.info("Successfully found all test answers for a question. Test question ID: "
                    + questionId);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all test answers for a question. Test question ID: "
                    + questionId, e);
            throw new DBException(ErrorType.GET, questionId);
        }
    }

    /**
     * Get all answers from all test subjects for a given test.
     *
     * @param testID - The given test ID.
     * @return - A list of all answers from all test subjects to the given test.
     */
    public List<TestAnswer> findAllTestAnswersForATest(long testID) throws DBException {
        try {
            List<TestAnswer> toReturn = dao.getTestAnswers(testID);
            logger.info("Successfully found all test answers for a test. Test ID: "
                    + testID);
            return toReturn;
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to find all test answers for a test. Test ID: "
                    + testID, e);
            throw new DBException(ErrorType.GET, testID);
        }
    }
}
