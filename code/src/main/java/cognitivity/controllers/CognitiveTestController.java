package cognitivity.controllers;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.services.CognitiveTestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST service for Cognitive Tests - allows to update, create, search and delete for cognitive tests for a
 * specific test manager or in general - by test id.
 */
@RestController
@RequestMapping("tests")
public class CognitiveTestController extends AbstractRestController<CognitiveTestService> {

    public CognitiveTestController() { super(new CognitiveTestService());}


    /**
     * Method for searching for a cognitive test.
     *
     * Params are as in CognitiveTestService.
     * If testId is null, then return all tests.
     *
     * @return - Cognitive test(s) for the test manager.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<CognitiveTest> findTestsForTestManager(
            @RequestParam(value = "testId", required = false) Long testId,
            @RequestParam(value = "testManagerId") long testManagerId) {
        List<CognitiveTest> result = new ArrayList<CognitiveTest>();
        if (testId == null) {
            // Then return all tests
            result = service.findTestsForTestManager(testManagerId);
        } else {
            // Then return one test.
            result.add(service.findTestForTestManagerById(testId, testManagerId));
        }
        return result;
    }


    /**
     * Method for saving (update / create) tests.
     *
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveCognitiveTest(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId,
            @RequestBody CognitiveTestDAO test) {

        if (testId == null) {
            service.createTestForTestManager(test, testManagerId);
        } else {
            service.updateTestForTestManager(testId, test, testManagerId);
        }

    }

    /**
     * Method for delete tests.
     *
     * Params are as in CognitiveTestService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCognitiveTest(@RequestParam long testID,
                                    @RequestParam long testManagerID) {
        service.deleteTestForTestManager(testID, testManagerID);
    }

}
