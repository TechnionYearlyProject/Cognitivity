package cognitivity.dao;

import cognitivity.entities.TestBlock;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore("need to debug")
public class TestBlockDAOTest {
    TestBlockDAO testBlockDAO;
    TestBlock testBlock;

    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add block to the db
     *      we check if we succeed by trying to fetch the block by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer steel in the db
     */
    @Test
    void crudTests(){
        assertNull(testBlockDAO.get(0L));
        testBlockDAO.add(testBlock);
        assertNotNull("add testBlock problem", testBlockDAO.get(testBlock.getId()));
        int numberOfQuestions = testBlock.getNumberOfQuestions();
        assertTrue("numberOfQuestions incorrect",
                numberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        int newNumberOfQuestions = 2;
        testBlock.setNumberOfQuestions(newNumberOfQuestions);
        testBlockDAO.update(testBlock);
        assertTrue("testBlock update incorrect",
                newNumberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        testBlock.setNumberOfQuestions(numberOfQuestions);
        testBlockDAO.update(testBlock);
        assertTrue("finalAnswer incorrect",
                numberOfQuestions == testBlockDAO.get(testBlock.getId()).getNumberOfQuestions());
        testBlockDAO.delete(testBlock.getId());
        assertNull("delete problem", testBlockDAO.get(testBlock.getId()));
    }
}
