package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test manager related operations.
 * @Author - Pe'er
 * @Date - 2.2.18
 *
 */

@Service
public class TestManagerService {

    private final static Logger logger = Logger.getLogger(TestManagerService.class);

    private TestManagerDAO dao;
    private CognitiveTestDAO testDao;

    @Autowired
    public TestManagerService(TestManagerDAO dao, CognitiveTestDAO testDao) {
        this.dao = dao;
        this.testDao = testDao;
    }

    /**
     * Save a TestManager.
     *
     * @param  - The testManager to save.
     * @return - The saved TestManager.
     * <p>
     * This will be used in conjunction with the POST HTTP method.
     */
    public TestManager createTestManager(TestManager testManager) throws DBException {
        try {
            dao.add(testManager);
            logger.info("Successfully added TestManager. TestManagerId = " + testManager.getId());
            return testManager;
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, testManager.getId());
        }
    }

    /**
     * Update a TestManager.
     *
     * @param testManager - The test manager to be updated.
     * <p>
     * This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestManager(TestManager testManager)throws DBException {
        try{
            dao.update(testManager);
            logger.info("Successfully updated TestManager. TestManagerId = " + testManager.getId());
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, testManager.getId());
        }
    }

    /**
     * Delete a TestManager.
     *
     * @param testManagerId - The test manager's id to delete.
     *                      <p>
     *                      This will be used in conjunction with the DELETE HTTP method.
     */
    public void deleteTestManager(long testManagerId)throws DBException {
        try{
            dao.delete(testManagerId);
            logger.info("Successfully deleted TestManager. TestManagerId = " + testManagerId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.UPDATE, testManagerId);
        }
    }


    /**
     * Find a test manager by its id.
     *
     * @param testManagerId - The test manager's id.
     * @return - the test manager found.
     */
    public TestManager findTestManager(long testManagerId)throws DBException {
        try {
            return dao.get(testManagerId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, testManagerId);
        }
    }

    /**
     * Find test manager who created a test with the given id.
     *
     * @param testId - The test id the test manager created.
     * @return - the test manager that created the test.
     * # Could return empty search result
     */
    public TestManager findTestManagerByCreatedTest(long testId)throws DBException {
        try {
            CognitiveTest test = testDao.get(testId);
            long managerId = test.getManager().getId();
            return dao.get(managerId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, testId);
        }
    }

    public long getManagerIdByEmail(String email)throws DBException {
        try {
            return dao.getIdFromEmail(email);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, null);
        }
    }


    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The test manager Id.
     * @return - All tests the test manager has created.
     */
    public List<CognitiveTest> findTestsForTestManager(long managerId)throws DBException {
        try {
            return testDao.getCognitiveTestOfManager(managerId);
        }catch (org.hibernate.HibernateException e){
            logger.error(e.getMessage());
            throw new DBException(ErrorType.GET, managerId);
        }
    }


}
