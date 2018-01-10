package cognitivity.dao;

import cognitivity.entities.TestManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore("need to debug")
public class TestManagerDAOTest {
    TestManagerDAO testManagerDAO;
    TestManager testManager;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testManagerDAO
     *  - testManager
     *
     */
    @Before
    public void initialize(){
        testManagerDAO = new TestManagerDAO();
        testManager =
                new TestManager("BB", "notarealpassword");

    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add testManager to the db
     *      we check if we succeed by trying to fetch the manager by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     */
    @Test
    void crudTests(){
        assertNull(testManagerDAO.get(0L));
        testManagerDAO.add(testManager);
        assertNotNull("add testManager problem", testManagerDAO.get(testManager.getId()));
        String testManagerName = testManager.getName();
        assertTrue("name incorrect",
                testManagerName == testManagerDAO.get(testManager.getId()).getName());
        String newTestManagerName = "john nash";
        testManager.setName(newTestManagerName);
        testManagerDAO.update(testManager);
        assertTrue("testManager update incorrect",
                newTestManagerName == testManagerDAO.get(testManager.getId()).getName());
        testManager.setName(testManagerName);
        testManagerDAO.update(testManager);
        assertTrue("name incorrect",
                testManagerName == testManagerDAO.get(testManager.getId()).getName());
        testManagerDAO.delete(testManager.getId());
        assertNull("delete problem", testManagerDAO.get(testManager.getId()));
    }
}
