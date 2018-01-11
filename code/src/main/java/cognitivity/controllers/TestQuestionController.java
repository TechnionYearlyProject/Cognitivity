package cognitivity.controllers;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import cognitivity.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(TestQuestionController.baseMapping)
public class TestQuestionController extends AbstractRestController<QuestionService> {

    public static final String baseMapping = "/test-questions";

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
    @RequestMapping(method = RequestMethod.GET, value = "/findTestQuestionsForTestCriteriaById")
    public List<TestQuestion> findTestQuestionsForTestCriteriaById(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {
        List<TestQuestion> result;
        if (testId == null) {
            // Then return all questions of test manager
            result = service.findAllTestQuestionsFromManagerId(testManagerId);
        } else {
            // Then return question of that one test.
            result = service.findAllTestQuestionsFromTestId(testId);
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
    @RequestMapping(method = RequestMethod.POST, value = "/saveCognitiveTestQuestion")
    public void saveCognitiveTestQuestion(
            @RequestParam String question,
            @RequestParam  Integer questionType,
            @RequestParam Integer answer,
            @RequestParam String tag,
            @RequestParam TestBlock block,
            @RequestParam CognitiveTest project) {

        service.createTestQuestion(question,questionType,answer,tag,block,project);

    }


    /**
     * Method for updating test questions.
     *
     * Params are as in TestQuestionService.
     * If questionId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateCognitiveTestQuestion")
    public void updateCognitiveTestQuestion(
            @RequestParam TestQuestion question) {
        service.updateTestQuestion(question);
    }

    /**
     * Method for delete test questions.
     *
     * Params are as in TestQuestionService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCognitiveTest")
    public void deleteCognitiveTest(@RequestParam long questionId) {
        service.deleteTestQuestion(questionId);
    }

}
