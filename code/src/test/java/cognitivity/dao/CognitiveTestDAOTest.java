package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@Ignore("need to debug")
public class CognitiveTestDAOTest {
    private CognitiveTestDAO cognitiveTestDAO;
    private TestManager testManager;
    private CognitiveTest cognitiveTest;

    /*
     * the initialization creates (before this class tests runs") the following objects:
     *
     *  - cognitiveTestDAO
     *  - testManager
     *  - cognitiveTest :
     *      the state is just an arbitrary number choice
     *
     */
    @Before
    public void initialize(){
        cognitiveTestDAO = new CognitiveTestDAO();
        testManager =
                new TestManager("onlyForTests TestManager", "notarealpassword");
        cognitiveTest =
                new CognitiveTest("onlyForTests", testManager, 1, 0);
    }

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add cognitiveTest to the db
     *      we check if we succeed by trying to fetch the answer by id
     *  - Read : we call the get function with feu parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     *
     */
    @Test
    void crudTests(){
        assertNull(cognitiveTestDAO.get(0L));
        cognitiveTestDAO.add(cognitiveTest);
        assertNotNull("add cognitiveTest problem", cognitiveTestDAO.get(cognitiveTest.getId()));
        int state = cognitiveTest.getState();
        assertTrue("state incorrect",
                state == cognitiveTestDAO.get(cognitiveTest.getId()).getState());
        int newState = 2;
        cognitiveTest.setState(newState);
        cognitiveTestDAO.update(cognitiveTest);
        assertTrue("state update incorrect",
                newState == cognitiveTestDAO.get(cognitiveTest.getId()).getState());
        cognitiveTest.setState(state);
        assertTrue("state update incorrect",
                state == cognitiveTestDAO.get(cognitiveTest.getId()).getState());
        cognitiveTestDAO.update(cognitiveTest);
        cognitiveTestDAO.delete(cognitiveTest.getId());
        assertNull("delete problem", cognitiveTestDAO.get(cognitiveTest.getId()));
    }


    /*
     * checks the getCognitiveTestOfManager function:
     *     first, we check that there isn't any tests for the manager
     *     next, we check that the list updated when we add an test
     *     next, we check that the list don't change when we add an test from another manager
     *     at the end we the testManager test and check that the list is empty
     *     at the end we remove all the tests
     *
     */
    @Test
    void getCognitiveTestOfManager(){
        List<CognitiveTest> cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManager.getId());
        assertTrue("should return empty list", cognitiveTestList.isEmpty());

        cognitiveTestDAO.add(cognitiveTest);
        cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManager.getId());
        assertTrue("the cognitive test should be on the list",
                cognitiveTestList.contains(cognitiveTest));
        assertTrue("the cognitive test should be the only test on the list",
                cognitiveTestList.size() == 1);

        TestManager anotherTestManager = new TestManager("Donald Trump", "donaldDontNeedPassword");
        CognitiveTest anotherCognitiveTest =
                new CognitiveTest("onlyForTests", anotherTestManager, 2, 0);
        cognitiveTestDAO.add(anotherCognitiveTest);
        cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManager.getId());
        assertTrue("the cognitive test should be on the list",
                !cognitiveTestList.contains(anotherCognitiveTest));
        assertTrue("the cognitive test should be the only test on the list",
                cognitiveTestList.size() == 1);

        cognitiveTestDAO.delete(cognitiveTest.getId());
        cognitiveTestList = cognitiveTestDAO.getCognitiveTestOfManager(testManager.getId());
        assertTrue("the cognitive test should be the only test on the list",
                cognitiveTestList.size() == 0);

        cognitiveTestDAO.delete(anotherCognitiveTest.getId());
    }


    /*
     * checks the getTestQuestions function:
     *     make sure that the list that returns is empty
     *
     *     note :
     *        add and remove questions will be checked in the testQuestionDAOTests
     *
     */
    @Test
    void getTestQuestions(){
        assertTrue("the test question list should be empty",
                cognitiveTestDAO.getTestQuestions(cognitiveTest.getId()).isEmpty());
    }
}
