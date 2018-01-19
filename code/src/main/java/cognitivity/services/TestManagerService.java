package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test manager related operations.
 *
 */

@Service
public class TestManagerService {

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
            return testManager;
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
        }
    }

    /**
     * Update a TestManager.
     *
     * @param manager - The test manager to be updated.
     * <p>
     * This will be used in conjunction with the PUT HTTP method.
     */
    public void updateTestManager(TestManager manager)throws DBException {
        try{
            dao.update(manager);
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
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
        }catch (org.hibernate.HibernateException e){
            throw new DBException(ErrorType.UPDATE);
        }
    }


    /**
     * Find a test manager by its id.
     *
     * @param testManagerId - The test manager's id.
     * @return - the test manager found.
     */
    public TestManager findTestManager(long testManagerId) {
        return dao.get(testManagerId);
    }

    /**
     * Find test manager who created a test with the given id.
     *
     * @param testId - The test id the test manager created.
     * @return - the test manager that created the test.
     * # Could return empty search result
     */
    public TestManager findTestManagerByCreatedTest(long testId) {
        CognitiveTest test = testDao.get(testId);
        long managerId = test.getManager().getId();
        return dao.get(managerId);
    }

    public long getManagerIdByEmail(String email) {
        return dao.getId(email);
    }




    /**
     * Find all cognitive tests that a test manager has created.
     *
     * @param managerId - The test manager Id.
     * @return - All tests the test manager has created.
     */
    public List<CognitiveTest> findTestsForTestManager(long managerId) {
        return testDao.getCognitiveTestOfManager(managerId);
    }


}
