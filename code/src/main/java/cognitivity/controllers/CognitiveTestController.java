package cognitivity.controllers;

import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
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
    @RequestMapping(method = RequestMethod.GET, value = "/findTestsForTestManager")
    public List<TestWrapper> findTestsForTestManager(
            @RequestParam(value = "managerId") long managerId) {
        System.out.println(managerId);
        return service.findTestsForTestManager(managerId);
    }


    /**
     * Method for saving tests.
     * <p>
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveCognitiveTest")
    public CognitiveTest saveCognitiveTest(
            @RequestBody CognitiveTest cognitiveTest) {
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
    public void updateCognitiveTest(
            @RequestBody CognitiveTest test) {
        service.updateTestForTestManager(test);
    }

    /**
     * Method for delete tests.
     * <p>
     * Params are as in CognitiveTestService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCognitiveTest")
    public void deleteCognitiveTest(@RequestParam(value = "testId") long testId) {
        service.deleteTestForTestManager(testId);
    }

}
