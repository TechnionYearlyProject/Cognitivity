package cognitivity.controllers;

import cognitivity.entities.TestManager;
import cognitivity.services.TestManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(TestManagerController.baseMapping)
public class TestManagerController extends AbstractRestController<TestManagerService> {

    public static final String baseMapping = "/test-managers";

    public TestManagerController() {
        super(new TestManagerService());
    }

    /**
     * Method for searching for a test manager.
     * <p>
     * Params are as in TestManagerService.
     * If testId is null, then return just test manager by id.
     *
     * @return - test manager(s) for the test criteria.
     */
    //TODO:A single manager is returned in both cases (Since we can only assign a single manager to a project). Why is this a list?
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestManagersForTestCriteria")
    public List<TestManager> findTestManagersForTestCriteria(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {
        List<TestManager> result = new ArrayList<TestManager>();
        if (testId == null) {
            // Then return test manager with id
            result.add(service.findTestManager(testManagerId));

        } else {
            // Then return test manager who created the cognitive test.
            result.add(service.findTestManagerByCreatedTest(testId));
        }
        return result;
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
            @RequestParam TestManager manager) {
        service.updateTestManager( manager);
    }


    /**
     * Method for creating test managers.
     * <p>
     * Params are as in TestManagerService.
     * If testManagerId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestManager")
    public void saveTestManager(
            @RequestParam(value = "name")String name,
            @RequestParam(value = "password")String password) {
        service.createTestManager(name, password);
    }

    /**
     * Method for deleting test managers.
     * <p>
     * Params are as in TestManagerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTestManager")
    public void deleteTestManager(@RequestParam long testManagerId) {
        service.deleteTestManager(testManagerId);
    }

}
