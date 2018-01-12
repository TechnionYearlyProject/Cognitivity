package cognitivity.services;

import cognitivity.dao.TestAnswerDAOimpl;
import cognitivity.dao.TestSubjectDAOimpl;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for answers to test questions related operations.
 *
 */
@Service
public class TestAnswerService {

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
                                                   Integer timeToAnswer, Boolean timeMeasured, Boolean timeShowed, Boolean testeeExit) {
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
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
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
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
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
        dao.delete(id);
    }

    /**
     * Delete all answers for a question.
     *
     * @param questionId - the question Id whose answers we want to delete.
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    public void deleteAllTestAnswersForQuestion(long questionId) {
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
        List<TestAnswer> answers = dao.getTestAnswers(questionId);
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
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
        return dao.get(answerId);
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subjects Id.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public List<TestAnswer> findTestAnswersBySubject(long subjectId) {
        TestSubjectDAOimpl dao = new TestSubjectDAOimpl();
        return dao.getSubjectAnswers(subjectId);
    }

    /**
     * Find all test answers that a subject answered.
     *
     * @param subjectId - The test subject Id.
     * @param testId - The test Id from which we want to get the results.
     *
     * @return - All test answers that belong to the subject with the given id.
     * */
    public List<TestAnswer> findTestAnswersBySubjectInTest(long subjectId, long testId) {
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
        return dao.getTestSubjectAnswersInTest(subjectId, testId);
    }

    /**
     * Get all the answers for a given test question.
     *
     * @param questionId - The question Id whose answers we are looking for.'
     * @return - A list of all the answers for the question.
     */
    public List<TestAnswer> findAllTestAnswerForAQuestion(long questionId){
        TestAnswerDAOimpl dao = new TestAnswerDAOimpl();
        return dao.getTestAnswers(questionId);
    }
}
