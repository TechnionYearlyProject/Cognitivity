package cognitivity.controllers;

import cognitivity.entities.TestQuestion;
import cognitivity.exceptions.DBException;
import cognitivity.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.TestQuestionController.baseMapping;

/**
 * REST service for Test Question components - allows saving, managing, finding and relating questions in the DB.
 * <p>
 * Created by ophir on 17/12/17.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class TestQuestionController extends AbstractRestController<QuestionService> {

    public static final String baseMapping = "/test-questions";

    @Autowired
    public TestQuestionController(QuestionService service) {
        super(service);
    }

    /**
     * Method for searching for cognitive test questions.
     * <p>
     * If testId is -1, then return all questions of test manager.
     *
     * @return - Cognitive test(s) for the test manager.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestQuestionsForTestCriteriaById")
    public List<TestQuestion> findTestQuestionsForTestCriteriaById(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) long testId) throws DBException {
        applicationInsights.trackEvent("FindTestQuestionsForTestCriteriaById");
        if (testId == -1) {
            // Then return all questions of test manager
            return service.findAllTestQuestionsFromManagerId(testManagerId);
        } else {
            // Then return question of that one test.
            return service.findAllTestQuestionsFromTestId(testId);
        }
    }

    /**
     * Method for searching for cognitive test questions by id.
     *
     * @return - Cognitive test(s) for the test manager.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestQuestionById")
    public TestQuestion findTestQuestionById(
            @RequestParam(value = "testQuestionId") long testQuestionId) throws DBException {
        applicationInsights.trackEvent("FindTestQuestionById");
        return service.findQuestionById(testQuestionId);
    }

    /**
     * Method for saving (update / create) test questions.
     * <p>
     * Params are as in TestQuestionService.
     * If questionId is null, then create. otherwise - update.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveCognitiveTestQuestion")
    public TestQuestion saveCognitiveTestQuestion(
            @RequestBody TestQuestion testQuestion) throws DBException {
        applicationInsights.trackEvent("SaveCognitiveTestQuestion");
        return service.createTestQuestion(testQuestion);
    }


    /**
     * Method for updating test questions.
     * <p>
     * Params are as in TestQuestionService.
     * If questionId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateCognitiveTestQuestion")
    public void updateCognitiveTestQuestion(
            @RequestBody TestQuestion question) throws DBException {
        applicationInsights.trackEvent("UpdateCognitiveTestQuestion");
        service.updateTestQuestion(question);
    }

    /**
     * Method for delete test questions.
     * <p>
     * Params are as in TestQuestionService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCognitiveTestQuestion")
    public void deleteCognitiveTestQuestion(@RequestParam(value = "questionId") long questionId) throws DBException {
        applicationInsights.trackEvent("DeleteCognitiveTestQuestion");
        service.deleteTestQuestion(questionId);
    }

    /**
     * Method for searching picture link by question id.
     *
     * @return - The link to the picture.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET,
            value = "/findPictureLinkPerQuestion",
            produces = "text/plain")
    public String findPictureLinkPerQuestion(
            @RequestParam(value = "testQuestionId") long testQuestionId) throws DBException {
        applicationInsights.trackEvent("FindPictureLinkPerQuestion");
        return service.findPictureLinkPerQuestion(testQuestionId);
    }

}
