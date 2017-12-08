package cognitivity.controllers;

import cognitivity.dto.CognitiveTestDTO;
import cognitivity.model.CognitiveTest;
import cognitivity.model.RepositorySearchResult;
import cognitivity.services.CognitiveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * REST service for Cognitive Tests - allows to update, create, search and delete for cognitive tests for a
 * specific test manager or in general - by test id.
 */
@RestController
@RequestMapping("tests")
public class CognitiveTestController {

    private final CognitiveTestService service;

    @Autowired
    public CognitiveTestController(CognitiveTestService service) {
        this.service = service;
    }


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
    public List<CognitiveTestDTO> findTestsForTestManager(
            @RequestParam(value = "testManagerId") String testManagerId,
            @RequestParam(value = "testId", required = false) String testId) {

        if (StringUtils.isEmpty(testId)) {
            // Then return all tests
            RepositorySearchResult<CognitiveTest> result = service.findTestsForTestManager(testManagerId);
            return CognitiveTestDTO.mapFromCognitiveTestEntities(result.getResult());
        } else {
            // Then return one test.
            CognitiveTest test = service.findTestForTestManagerById(testId, testManagerId);
            return Collections.singletonList(CognitiveTestDTO.mapFromCognitiveTestEntity(test));
        }
    }

    /**
     * Method for saving (update / create) tests.
     *
     * Params are as in CognitiveTestService.
     * If testId is null, then create. otherwise - update.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveCognitiveTest(
            @RequestParam(value = "testManagerId") String testManagerId,
            @RequestParam(value = "testId", required = false) String testId,
            @RequestBody CognitiveTestDTO test) {

        if (StringUtils.isEmpty(testId)) {
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
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCognitiveTest(@RequestParam String testID,
                                    @RequestParam String testManagerID) {
        service.deleteTestForTestManager(testID, testManagerID);
    }


    /**
     * Error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
