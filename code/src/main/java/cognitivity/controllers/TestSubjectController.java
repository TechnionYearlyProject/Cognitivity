package cognitivity.controllers;

import cognitivity.entities.TestSubject;
import cognitivity.services.TestSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(TestSubjectController.baseMapping)
public class TestSubjectController extends AbstractRestController<TestSubjectService> {

    public static final String baseMapping = "/test-subjects";

    public TestSubjectController() {
        super(new TestSubjectService());
    }

    /**
     * Method for searching for a cognitive test subjects.
     * <p>
     * Params are as in TestSubjectService.
     * If testId is -1, then return just test subject by id.
     *
     * @return - test subject(s) for the test criteria.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestSubjectsForTestCriteria")
    public List<TestSubject> findTestSubjectsForTestCriteria(
            @RequestParam(value = "testSubjectId") long testSubjectId,
            @RequestParam(value = "testId", required = false) long testId) {
        if (testId == -1) {
            // Then return test subject with id
            return Collections.singletonList(service.findTestSubject(testSubjectId));
        } else {
            // Then return all test subjects who took the cognitive test.
            return service.findTestSubjectsWhoParticipatedInTest(testId);
        }
    }



    /**
     * Method for creating test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestSubject")
    public void saveTestSubject(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "ip") Integer ipAddress,
            @RequestParam(value = "browser") String browser) {
        service.createTestSubject(name, ipAddress, browser);
    }

    /**
     * Method for updating test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateTestSubject")
    public void updateTestSubject(
            @RequestBody TestSubject subject) {
        service.updateTestSubject(subject);
    }

    /**
     * Method for deleting test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTestSubject")
    public void deleteTestSubject(@RequestParam(value = "testSubjectId") long testSubjectId) {
        service.deleteTestSubject(testSubjectId);
    }

}
