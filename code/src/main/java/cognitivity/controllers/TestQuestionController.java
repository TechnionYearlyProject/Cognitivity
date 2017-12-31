package cognitivity.controllers;

import cognitivity.dao.TestQuestionDAO;
import cognitivity.entities.TestQuestion;
import cognitivity.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("test-questions")
public class TestQuestionController extends AbstractRestController<QuestionService> {

    public TestQuestionController() {
        super(new QuestionService());
    }

    /**
     * Method for searching for cognitive test questions.
     *
     * If testId is null, then return all questions of test manager.
     *
     * @return - Cognitive test(s) for the test manager.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestQuestion> findTestQuestionsForTestCriteriaById(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {
        List<TestQuestion> result;
        if (testId == null) {
            // Then return all questions of test manager
            result = service.findTestQuestionsForTestManagerById(testManagerId);
        } else {
            // Then return question of that one test.
            result = service.getTestQuestionsForTest(testId);
        }
        return result;
    }

    /**
     * Method for saving (update / create) test questions.
     *
     * Params are as in TestQuestionService.
     * If questionId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveCognitiveTestQuestion(
            @RequestParam(value = "questionId", required = false) Long questionId,
            @RequestBody TestQuestionDAO question) {

        if (questionId == null) {
            service.createTestQuestion(question);
        } else {
            service.updateTestQuestion(questionId, question);
        }
    }

    /**
     * Method for delete test questions.
     *
     * Params are as in TestQuestionService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCognitiveTest(@RequestParam long quesstionId) {
        service.deleteTestQuestion(quesstionId);
    }

}
