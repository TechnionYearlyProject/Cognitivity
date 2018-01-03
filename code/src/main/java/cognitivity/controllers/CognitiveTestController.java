package cognitivity.controllers;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
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
     * Method for searching for all cognitive tests of a manager.
     *
     * Params are as in CognitiveTestService.
     *
     * @return - Cognitive test(s) for the test manager.
     * */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<CognitiveTest> findTestsForTestManager(
            @RequestParam TestManager manager) {
        return service.findTestsForTestManager(manager);
    }


    /**
     * Method for saving tests.
     *
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveCognitiveTest(
            @RequestParam(value = "name") String name,
            @RequestParam TestManager manager,
            @RequestParam(value = "state") Integer state,
            @RequestParam(value = "numberOfQuestion") Integer numberOfQuestions){
        service.createTestForTestManager(name,manager,state,numberOfQuestions);

    }

    /**
     * Method for updating tests.
     *
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void updateCognitiveTest(
            @RequestParam CognitiveTest test){
        service.updateTestForTestManager(test);

    }

    /**
     * Method for delete tests.
     *
     * Params are as in CognitiveTestService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCognitiveTest(@RequestParam CognitiveTest test) {
        service.deleteTestForTestManager(test);
    }

}
