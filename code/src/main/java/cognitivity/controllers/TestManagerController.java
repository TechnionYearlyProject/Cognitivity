package cognitivity.controllers;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.dao.TestManager;
import cognitivity.dto.TestManagerDTO;
import cognitivity.services.TestManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-managers")
public class TestManagerController extends AbstractRestController<TestManagerService> {

    @Autowired
    public TestManagerController(TestManagerService service) {
        super(service);
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
    public List<TestManagerDTO> findTestManagersForTestCriteria(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {

        if (testId == null) {
            // Then return test manager with id
            TestManager result = service.findTestManager(testManagerId);
            return Collections.singletonList(TestManagerDTO.mapFromTestManagerEntity(result));
        } else {
            // Then return test manager who created the cognitive test.
            RepositorySearchResult<TestManager> result = service.findTestManagerByCreatedTest(testId);
            return TestManagerDTO.mapFromTestManagerEntities(result.getResult());
        }
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
            @RequestBody TestManagerDTO manager) {

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
