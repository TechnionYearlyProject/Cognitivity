package cognitivity.services;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestAnswerDAO;
import cognitivity.dao.TestManagerDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Business service for test questions related operations.
 *
 */

@Service
public class QuestionService {

    /**
     * Save a TestQuestion.
     *
     * @param question - The question itself.
     * @param questionType - The type of the created question.
     * @param answer - The answer to the created question.
     * @param tag - The question tag.
     * @param block - The block the question is related to.
     * @param project - The project the question is related to.
     * @return - The saved TestQuestion.
     *
     * This will be used in conjunction with the POST HTTP method.
     * */
    public TestQuestion createTestQuestion(String question, Integer questionType,
                                           Integer answer, String tag, TestBlock block, CognitiveTest project) {
        TestQuestionDAO dao = new TestQuestionDAO();
        TestQuestion q = new TestQuestion(question,questionType,answer,tag,block,project);
        dao.add(q);
        return q;
    }

    /**
     * Update a TestQuestion.
     *
     * @param q - The cognitive test question to be updated.
     *
     * This will be used in conjunction with the PUT HTTP method.
     * */
    public void updateTestQuestion(TestQuestion q) {
        TestQuestionDAO dao = new TestQuestionDAO();
        dao.update(q);
    }


    /**
     * Delete a TestQuestion.
     *
     * @param questionId - The test question Id to delete.
     *
     * Important Note: This will delete all answers associated with the question! (maybe)
     *
     * This will be used in conjunction with the DELETE HTTP method.
     * */
    //TODO: do we want to delete all corresponding test answers as well?
    public void deleteTestQuestion(long questionId) {
        TestQuestionDAO dao = new TestQuestionDAO();
        dao.delete(questionId);
    }

    /**
     * find a specific question by its id.
     *
     * @param id - the question id.
     * @return the question corresponding to the given id if it exists, null otherwise
     */
    public TestQuestion findQuestionById(long id){
        TestQuestionDAO dao = new TestQuestionDAO();
        return dao.get(id);
    }

    /**
     * Get all answers to a given question
     *
     * @param questionId - The test question we want to get the answer to.
     *
     * @return - All the answers to the given question.
     */
    public List<TestAnswer> getTestAnswers(long questionId){
        TestAnswerDAO dao = new TestAnswerDAO();
        return dao.getTestAnswers(questionId);
    }

    /**
     * Get all test questions from a given test.
     *
     * @param testId - The test Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromTestId(long testId){
        CognitiveTestDAO cognitiveDAO = new CognitiveTestDAO();
        CognitiveTest test = cognitiveDAO.get(testId);
        return cognitiveDAO.getTestQuestions(testId);
    }

    /**
     * Get all test questions from a given manager.
     *
     * @param managerId - The manager Id from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    public List<TestQuestion> findAllTestQuestionsFromManagerId(long managerId){
        TestQuestionDAO questionDAO = new TestQuestionDAO();
        TestManagerDAO testManagerDAO = new TestManagerDAO();
        TestManager testManager = testManagerDAO.get(managerId);
        return questionDAO.getTestQuestionsFromAManager(testManager);
    }
}
