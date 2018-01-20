package cognitivity.dao;

import cognitivity.entities.TestManager;
import cognitivity.web.app.config.CognitivityMvcConfiguration;
import config.ObjectMapperBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CognitivityMvcConfiguration.class})
//@Ignore("tests passing, but to run them there is a need of db")
public class TestManagerDAOTest extends AbstractDaoTestClass {

    private TestManager testManager;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - testManagerDAO
     *  - testManager
     *
     */
    @Before
    public void initialize(){
        testManager =
                new TestManager("BB notarealpassword");

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
    public void crudTests(){
        assertNull(testManagerDAO.get(0L));
        testManagerDAO.add(testManager);
        assertNotNull("add testManager problem", testManagerDAO.get(testManager.getId()));
        String testManagerName = testManager.getEmail();
        assertTrue("email incorrect",
                testManagerName.equals(testManagerDAO.get(testManager.getId()).getEmail()));
        String newTestManagerName = "john nash";
        testManager.setEmail(newTestManagerName);
        testManagerDAO.update(testManager);
        assertTrue("testManager update incorrect",
                newTestManagerName.equals(testManagerDAO.get(testManager.getId()).getEmail()));
        testManager.setEmail(testManagerName);
        testManagerDAO.update(testManager);
        assertTrue("name incorrect",
                testManagerName.equals(testManagerDAO.get(testManager.getId()).getEmail()));
        testManagerDAO.delete(testManager.getId());
        assertNull("delete problem", testManagerDAO.get(testManager.getId()));
    }


    /*
     * basic test for the getIdFromEmail function,
     * from email we should get the id of the manager
     */
    @Test
    public void getId(){
        testManagerDAO.add(testManager);
        Long managerId = testManager.getId();
        Long res = testManagerDAO.getIdFromEmail("BB notarealpassword");
        assertTrue(res.equals(managerId));
        testManagerDAO.delete(testManager.getId());
    }

}
