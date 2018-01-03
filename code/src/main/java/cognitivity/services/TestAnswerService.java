package cognitivity.services;

import cognitivity.dao.TestAnswerDAO;
import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * Business service for answers to test questions related operations.
 *
 */
public class TestAnswerService extends AbstractService {

    @Autowired
    public TestAnswerService(TestAnswer repository) {
    }

    public TestAnswerService() {

    }

    /**
     * Create a test answer, and add it to the DB.
     *
     * @param testSubject - The test subject who answered the test.
     * @param question - The question the answer relates to
     * @param cognitiveTest - The test the answer relates to
     * @param numberOfClick - The number of clicks the test subject clicked while answering the question.
     * @param finalAnswer - The final answer for the answer.
     * @param questionPlacement - The placement for the question in the page.
     * @param answerPlacement - The placement of the answer in the page.
     * @param verbalAnswer - The verbal answer for the question.
     * @param questionWithPicture - A boolean that holds true if the question has a picture.
     * @param timeToAnswer - Time that took to answer thw question.
     * @param timeMeasured - A boolean that holds true if time was measured for this question.
     * @param timeShowed - A boolean that holds true if the measured time was shown for this question.
     * @param testeeExit - A boolean that holds true if the test subject exited the test.
     *
     * @return - The created test answer.
     */
    public TestAnswer addTestAnswerForTestQuestion( TestSubject testSubject,
                                                   TestQuestion question, CognitiveTest cognitiveTest,
                                                   Integer numberOfClick, Integer finalAnswer, Integer questionPlacement,
                                                   Integer answerPlacement, String verbalAnswer, Boolean questionWithPicture,
                                                   String timeToAnswer, Boolean timeMeasured, Boolean timeShowed, Boolean testeeExit) {
        TestAnswerDAO dao = new TestAnswerDAO();
        TestAnswer answer = new TestAnswer(testSubject,question,cognitiveTest,numberOfClick,finalAnswer,questionPlacement,
                answerPlacement,verbalAnswer,questionWithPicture,timeToAnswer,timeMeasured,timeShowed, testeeExit);
        dao.add(answer);
        return answer;
    }

    /**
     * Update a TestAnswer for a question.
     *
     * @param answer - The test answer to update.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestAnswerForQuestion(TestAnswer answer) {
        TestAnswerDAO dao = new TestAnswerDAO();
        dao.update(answer);
    }

    /**
     * Delete a TestAnswer for a question.
     *
     * @param id - The test answer id to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteTestAnswerForQuestion(long id) {
        TestAnswerDAO dao = new TestAnswerDAO();
        dao.delete(id);
    }

    /**
     * Delete all answers for a question.
     *
     * @param question Whose answers we want to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteAllTestAnswersForQuestion(TestQuestion question) {
        TestAnswerDAO dao = new TestAnswerDAO();
        List<TestAnswer> answers = dao.getTestAnswers(question);
        for (TestAnswer answer : answers){
            dao.delete(answer.getId());
        }
    }

    /**
     * Find test answer with given id.
     *
     * @param answerId - The test answer's id.
     * @return - Test answer with given id, null if it doesn't exist.
     * */
    public TestAnswer findTestAnswerById(long answerId) {
        TestAnswerDAO dao = new TestAnswerDAO();
        return dao.get(answerId);
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subject - The test subject.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public List<TestAnswer> findTestAnswersBySubject(TestSubject subject) {
        TestSubjectDAO dao = new TestSubjectDAO();
        return dao.getSubjectAnswers(subject);
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subject - The test subject.
     * @param test - The test from which we want to get the results.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public List<TestAnswer> findTestAnswersBySubjectInTest(TestSubject subject, CognitiveTest test) {
        TestAnswerDAO dao = new TestAnswerDAO();
        return dao.getTestSubjectAnswersInTest(subject, test);
    }

    /**
     * Get all the answers for a given test question.
     *
     * @param question - The question whose answers we are looking for.'
     * @return - A list of all the answers for the question.
     */
    public List<TestAnswer> findAllTestAnswerForAQuestion(TestQuestion question){
        TestAnswerDAO dao = new TestAnswerDAO();
        return dao.getTestAnswers(question);
    }
}
