package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test subject related operations.
 * @Author - Pe'er
 * @Date - 2.2.18
 *
 */

@Service
public class TestSubjectService {

    private final static Logger logger = Logger.getLogger(TestSubjectService.class);

    private TestSubjectDAO dao;

    @Autowired
    public TestSubjectService(TestSubjectDAO dao) {
        this.dao = dao;
    }
    /**
     * Save a TestSubject.
     *
     * @param testSubject - The TestSubject to be saved
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestSubject createTestSubject(TestSubject testSubject)throws DBException {
        try {
            dao.add(testSubject);
            logger.info("Successfully added TestSubject. TestSubjectID: "
                    + testSubject.getId());
            return testSubject;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to add TestSubject. TestSubjectID: " + testSubject.getId(),e);
            throw new DBException(ErrorType.SAVE,testSubject.getId());
        }
    }

    /**
     * Update a TestSubject.
     *
     * @param testSubject - The test subject to update.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestSubject(TestSubject testSubject)throws DBException {
        try {
            dao.update(testSubject);
            logger.info("Successfully updated TestSubject. TestSubjectID: "
                    + testSubject.getId());
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to update TestSubject. TestSubjectID: " + testSubject.getId(),e);
            throw new DBException(ErrorType.UPDATE,testSubject.getId());
        }
    }

    /**
     * Delete a TestSubject.
     *
     * @param testSubjectId - The test subject's id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestSubject(long testSubjectId)throws DBException {
        try {
            dao.delete(testSubjectId);
            logger.info("Successfully deleted TestSubject. TestSubjectID: "
                    + testSubjectId);
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to delete TestSubject. TestSubjectID: " + testSubjectId,e);
            throw new DBException(ErrorType.UPDATE, testSubjectId);
        }
    }


    /**
     * Find a test subject by its id.
     *
     * @param testSubjectId - The test subject's id.
     * @return - the test subject found.
     */
    public TestSubject findTestSubject(long testSubjectId)throws DBException {
        try{
            TestSubject toReturn = dao.get(testSubjectId);
            if(toReturn == null){
                logger.error("Failed to find TestSubject. TestSubject with ID: " + testSubjectId+" doesn't exist");
                throw new DBException(ErrorType.GET, testSubjectId);
            }
            logger.info("Successfully found TestSubject. TestSubjectID: "
                    + testSubjectId);
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to find TestSubject. TestSubjectID: " + testSubjectId,e);
            throw new DBException(ErrorType.GET, testSubjectId);
        }
    }

    /**
     * Find all answers given by a specific test subject
     *
     * @param subjectId - the test subject Id.
     * @return - all the answers the test subject gave.
     */
    public List<TestAnswer> findAllTestSubjectAnswers(long subjectId)throws DBException{
        try{
            if(dao.get(subjectId) == null){
                logger.error("Failed to find all TestSubjects' answers. TestSubject with ID: "
                        + subjectId+" doesn't exist");
                throw new DBException(ErrorType.DOESNT_EXIST, subjectId);
            }
            List<TestAnswer> toReturn = dao.getSubjectAnswers(subjectId);
            logger.info("Successfully found all TestSubjects' answers. TestSubjectID: "
                    + subjectId);
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to find all TestSubjects' answers. TestSubjectID: "
                    + subjectId,e);
            throw new DBException(ErrorType.GET, subjectId);
        }
    }


    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    public List<TestSubject> findTestSubjectsWhoParticipatedInTest(long testId)throws DBException{
        try{
            List<TestSubject> toReturn = dao.getTestSubjectsWhoParticipatedInTest(testId);
            logger.info("Successfully found all TestSubjects who participated in a test. TestID: "
                    + testId);
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to find all TestSubjects who participated in a test. TestID: "
                    + testId,e);
            throw new DBException(ErrorType.GET, testId);
        }
    }

    /**
     *
     * Get all test subjects in the system.
     *
     * @return - A list containing all test subjects in the system.
     */
    public List<TestSubject> findAllTestSubjectsInTheSystem() throws DBException{
        try{
            List<TestSubject> toReturn = dao.findAllTestSubjectsInTheSystem();
            logger.info("Successfully found all TestSubjects in the system.");
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to find all TestSubjects in the system "
                    ,e);
            throw new DBException(ErrorType.GET, null);
        }

    }


}
