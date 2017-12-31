package cognitivity.controllers;

import cognitivity.dao.TestManagerDAO;
import cognitivity.entities.TestManager;
import cognitivity.services.TestManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("test-managers")
public class TestManagerController extends AbstractRestController<TestManagerService> {

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
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestManager> findTestManagersForTestCriteria(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {
        List<TestManager> result = new ArrayList<TestManager>();
        if (testId == null) {
            // Then return test manager with id
            result.add(service.findTestManager(testManagerId));

        } else {
            // Then return test manager who created the cognitive test.
            result = service.findTestManagerByCreatedTest(testId);
        }
        return result;
    }

    /**
     * Method for saving (update / create) test managers.
     * <p>
     * Params are as in TestManagerService.
     * If testManagerId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestManager(
            @RequestParam(value = "testManagerId", required = false) Long testManagerId,
            @RequestBody TestManagerDAO manager) {

        if (testManagerId == null) {
            service.createTestManager(manager);
        } else {
            service.updateTestManager(testManagerId, manager);
        }

    }

    /**
     * Method for deleting test managers.
     * <p>
     * Params are as in TestManagerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTestManager(@RequestParam long testManagerId) {
        service.deleteTestManager(testManagerId);
    }

}
