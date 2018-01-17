package cognitivity.dao;


import cognitivity.entities.*;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateBeanConfiguration.class,
        locations = {"classpath:testApplicationContext.xml", "classpath:test-dispatcher-servlet.xml"})
@Ignore("tests passing, but to run them there is a need of db")
public class TestSubjectDAOTest extends AbstractDaoTestClass {

    private int numOfSubjects = 5;
    private int numOfAnswersPerSubject = 5;
    private CognitiveTest cognitiveTest;
    private TestManager testManager;
    private TestSubject testSubject[];
    private TestBlock testBlock;
    private TestAnswer testAnswers[][];

    @Before
    public void initialize(){
        testSubject = new TestSubject[numOfSubjects];
        testAnswers = new TestAnswer[numOfSubjects][numOfAnswersPerSubject];
        testManager = new TestManager("qertyt", "      ");
        testManagerDAO.add(testManager);
        cognitiveTest = new CognitiveTest("newTest", testManager,0,0);
        cognitiveTestDAO.add(cognitiveTest);
        testBlock = new TestBlock(0, true, "tag", cognitiveTest);
        testBlockDAO.add(testBlock);

        TestQuestion testQuestion = new TestQuestion("bkabalba", 2,2,
                "thisIsMyTag", testBlock, cognitiveTest, testManager);
        testQuestionDAO.add(testQuestion);
        for(int i = 0; i < numOfSubjects; i++){
            testSubject[i] = new TestSubject("subject", 3 + i, "firefox");
            testSubjectDAO.add(testSubject[i]);
            for(int j = 0; j < numOfAnswersPerSubject; j++){
                testAnswers[i][j] = new TestAnswer(testSubject[i], testQuestion,
                        cognitiveTest, 1, j,1,
                        1, null, false,
                        5, false, false, false);
            }
        }
    }


    /*
     * This test will check only the basic CRUD functionality:
     *
     *  - Create : we call the add function and trying to add testSubject to the db
     *      we check if we succeed by trying to fetch the subject by id
     *  - Read : we call the get function with fue parameters,
     *      once, with id that don't exists, one with id that do exists
     *  - Update : we call the update function and check that the data in the db changed
     *  - Delete : we call the delete function and delete if the answer still in the db
     *
     */
    @Test
    public void crudTests(){
        assertNull(testSubjectDAO.get(0L));
        testSubjectDAO.add(testSubject[0]);
        assertNotNull("add testSubject problem", testSubjectDAO.get(testSubject[0].getId()));
        String testSubjectBrowser = testSubject[0].getBrowser();
        assertTrue("browser incorrect",
                testSubjectBrowser.equals(testSubjectDAO.get(testSubject[0].getId()).getBrowser()));
        String newTestSubjectBrowser = "explorer";
        testSubject[0].setBrowser(newTestSubjectBrowser);
        testSubjectDAO.update(testSubject[0]);
        assertTrue("browser incorrect",
                newTestSubjectBrowser.equals(testSubjectDAO.get(testSubject[0].getId()).getBrowser()));
        testSubject[0].setBrowser(testSubjectBrowser);
        testSubjectDAO.update(testSubject[0]);
        assertTrue("browser incorrect",
                testSubjectBrowser.equals(testSubjectDAO.get(testSubject[0].getId()).getBrowser()));
        testSubjectDAO.delete(testSubject[0].getId());
        assertNull("delete problem", testSubjectDAO.get(testSubject[0].getId()));
    }


    /*
     * This test will check getSubjectAnswers function:
     *
     *  - for each subject, we check by loop that the number of answers that the
     *     subject has answered equals to the number of his answers in the db
     *     (each time we add answer)
     *  - we removing all the answers, while after each remove we validating that the value
     *     is not on the returned list.
     *  - we update the question to be question of another subject, and we validate that the
     *     subject question list is empty
     *  - we check that the new subject have the question in its list
     *  - we remove the question back and validate that both subjects don't return empty lists
     *
     */
    @Test
    public void getSubjectAnswers(){
        for(int j = 0; j < numOfSubjects; j++){
            for(int i = 0; i <= numOfAnswersPerSubject; i++){
                assertTrue("Subject should have " + i + " answers",
                        testSubjectDAO.getSubjectAnswers(testSubject[j].getId()).size() == i);
                // validating that all the answers the subject have so far stayed in the list
                for(int k = 0; k < i; k++){
                    assertTrue("Subject don't have the " + i + "-th answer",
                       testSubjectDAO.getSubjectAnswers(testSubject[j].getId()).contains(testAnswers[j][k]));
                }
                // on the last iteration we just want to check that the last answer is fine,
                // we don't have any more answers to insert
                if(i < numOfAnswersPerSubject)
                    testAnswerDAO.add(testAnswers[j][i]);
            }
        }

        for(int j = 0; j < numOfSubjects; j++){
            for(int i = numOfAnswersPerSubject; i >= 0; i--){
                assertTrue("Subject should have " + i +" answers",
                        testSubjectDAO.getSubjectAnswers(testSubject[j].getId()).size() == i);
                // on the last iteration we just want to check that there are no more answers to any subject
                if(i > 0)
                    testAnswerDAO.delete(testAnswers[j][i - 1].getId());
            }
        }


        testAnswerDAO.add(testAnswers[0][0]);
        //changing the subject to make sure the subject list updates
        testAnswers[0][0].setTestSubject(testSubject[1]);
        testAnswerDAO.update(testAnswers[0][0]);
        assertTrue("Subjects list should be empty",
                testSubjectDAO.getSubjectAnswers(testSubject[0].getId()).isEmpty());
        assertTrue("Subjects list should contain the answer we just added",
                testSubjectDAO.getSubjectAnswers(testSubject[1].getId()).size() == 1);
        testAnswerDAO.delete(testAnswers[0][0].getId());
        assertTrue("Subjects list should contain the answer we just added",
                testSubjectDAO.getSubjectAnswers(testSubject[1].getId()).isEmpty());
        //returning the original subject
        testAnswers[0][0].setTestSubject(testSubject[0]);
    }


    /*
     * This test will check getTestSubjectsWhoParticipatedInTest function:
     *
     *  - we add test and each time we add one answer from subject, we
     *     validating the result stays the same each time
     *  - we remove all the participant answers and make sure that the list stays the same
     *  - we add participates(more then one) to the test and validate that the list contains them
     *  - we remove all the answers and finish
     *  
     */
    @Test
    public void getTestSubjectsWhoParticipatedInTest(){
        cognitiveTestDAO.add(cognitiveTest);
        assertTrue("Test should have empty list of participants",
                testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).isEmpty());
        for(int i = 0; i < numOfAnswersPerSubject; i++){
            testAnswerDAO.add(testAnswers[0][i]);
            assertTrue("Test should have only one participant",
                    testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).size() == 1);
            assertTrue(testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).contains(testSubject[0]));
        }
        for(int i = 0; i < numOfAnswersPerSubject; i++){
            assertTrue("Test should have only one participant",
                    testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).size() == 1);
            assertTrue(testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).contains(testSubject[0]));
            testAnswerDAO.delete(testAnswers[0][i].getId());
        }
        assertTrue("Test should have empty list of participants",
                testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).isEmpty());

        for(int i = 0; i < numOfSubjects; i++){
            testAnswerDAO.add(testAnswers[i][0]);
            assertTrue("Test should have " + (i + 1) + " participant",
                    testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).size() == (i + 1));
        }

        // removing all the answers
        for(int i = numOfSubjects - 1; i >= 0; i--){
            testAnswerDAO.delete(testAnswers[i][0].getId());
            assertTrue("Test should have " + i + " participant",
                    testSubjectDAO.getTestSubjectsWhoParticipatedInTest(cognitiveTest.getId()).size() == i);
        }

    }
}
