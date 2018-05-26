package cognitivity.controllers;

import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.exceptions.DBException;
import cognitivity.services.CognitiveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.CognitiveTestController.baseMapping;

/**
 * REST service for Cognitive Tests - allows to update, create, search and delete for cognitive tests for a
 * specific test manager or in general - by test id.
 *
 * Created by ophir on 17/12/17.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class CognitiveTestController extends AbstractRestController<CognitiveTestService> {

    public static final String baseMapping = "/tests";


    @Autowired
    public CognitiveTestController(CognitiveTestService service) {
        super(service);
    }


    /**
     * Method for searching for all cognitive tests of a manager.
     * <p>
     * Params are as in CognitiveTestService.
     *
     * @return - Cognitive test(s) for the test manager.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findCognitiveTestById")
    public TestWrapper findCognitiveTestById(
            @RequestParam(value = "testId") long testId) throws DBException {
        applicationInsights.trackEvent("FindCognitiveTestById");
        return service.findTestById(testId);
    }

    /**
     * Method for searching for all cognitive tests of a manager without fetching the questions.
     * <p>
     * Params are as in CognitiveTestService.
     *
     * @return - Cognitive test(s) for the test manager.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestsForTestManagerWithoutQuestions")
    public List<CognitiveTest> findTestsForTestManagerWithoutQuestions(
            @RequestParam(value = "managerId") long managerId) throws DBException {
        applicationInsights.trackEvent("FindTestsForTestManagerWithoutQuestions");
        return service.findTestsForTestManagerWithoutQuestions(managerId);
    }

    /**
     * Method for getting all tests of a specific project.
     * <p>
     * Params are as in CognitiveTestService.
     *
     * @return - Cognitive test(s) of a project.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/filterTestsByProject")
    public List<CognitiveTest> filterTestsByProject(
            @RequestParam(value = "project") String projectFilter) throws DBException {
        applicationInsights.trackEvent("FilterTestsByProject");
        return service.filterTestsByProject(projectFilter);
    }


    /**
     * Method for getting all tests with a specific description in notes field.
     * <p>
     * Params are as in CognitiveTestService.
     *
     * @return - Cognitive test(s) with a description that <ul>contains</ul> the passed 'notes' string.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/filterTestsByNotes")
    public List<CognitiveTest> filterTestsByNotes(
            @RequestParam(value = "notes") String notes) throws DBException {
        applicationInsights.trackEvent("FilterTestsByNotes");
        return service.filterTestsByNotes(notes);
    }


    /**
     * Method for saving tests.
     * <p>
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveCognitiveTest")
    public TestWrapper saveCognitiveTest(
            @RequestBody TestWrapper cognitiveTest) throws DBException {
        applicationInsights.trackEvent("SaveCognitiveTest");
        return service.createTestForTestManager(cognitiveTest);
    }

    /**
     * Method for updating tests.
     * <p>
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateCognitiveTest")
    public TestWrapper updateCognitiveTest(
            @RequestBody TestWrapper test) throws DBException {
        applicationInsights.trackEvent("UpdateCognitiveTest");
        return service.updateTestForTestManager(test);
    }

    /**
     * Method for delete tests.
     * <p>
     * Params are as in CognitiveTestService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCognitiveTest")
    public void deleteCognitiveTest(@RequestParam(value = "testId") long testId) throws DBException {
        applicationInsights.trackEvent("DeleteCognitiveTest");
        service.deleteTestForTestManager(testId);
    }
}
