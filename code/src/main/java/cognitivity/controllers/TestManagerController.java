package cognitivity.controllers;

import cognitivity.entities.TestManager;
import cognitivity.exceptions.DBException;
import cognitivity.services.TestManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.TestManagerController.baseMapping;

/**
 * REST service for Test Managers - offers operation on test managers.
 * <p>
 * Created by ophir on 17/12/17.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class TestManagerController extends AbstractRestController<TestManagerService> {

    public static final String baseMapping = "/test-managers";

    @Autowired
    public TestManagerController(TestManagerService service) {
        super(service);
    }

    /**
     * Method for searching for a test manager.
     * <p>
     * Params are as in TestManagerService.
     * If testId is -1, then return just test manager by id.
     *
     * @return - test manager(s) for the test criteria.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestManagersForTestCriteria")
    public TestManager findTestManagersForTestCriteria(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) long testId)throws DBException {
        if (testId == -1) {
            // Then return test manager with id
            return service.findTestManager(testManagerId);
        } else {
            // Then return test manager who created the cognitive test.
            return service.findTestManagerByCreatedTest(testId);
        }
    }

    /**
     * Method for finding id by email.
     * <p>
     * Params are as in TestManagerService.
     *
     * @return - test manager's id for the email received.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestManagerIdByEmail")
    public long findTestManagerIdByEmail(
            @RequestParam(value = "email") String email)throws DBException {
        return service.getManagerIdByEmail(email);
    }

    /**
     * Method for updating test managers.
     * <p>
     * Params are as in TestManagerService.
     * If testManagerId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateTestManager")
    public void updateTestManager(
            @RequestBody TestManager manager)throws DBException {
        service.updateTestManager(manager);
    }


    /**
     * Method for creating test managers.
     * <p>
     * Params are as in TestManagerService.
     * If testManagerId is null, then create. otherwise - update.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestManager")
    public TestManager saveTestManager(@RequestBody TestManager testManager)throws DBException {
        return service.createTestManager(testManager);
    }

    /**
     * Method for deleting test managers.
     * <p>
     * Params are as in TestManagerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTestManager")
    public void deleteTestManager(@RequestParam(value = "testManagerId") long testManagerId)throws DBException {
        service.deleteTestManager(testManagerId);
    }

}
