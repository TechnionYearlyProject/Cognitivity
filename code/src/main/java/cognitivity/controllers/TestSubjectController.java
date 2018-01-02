package cognitivity.controllers;

import cognitivity.entities.TestSubject;
import cognitivity.services.TestSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("test-subjects")
public class TestSubjectController extends AbstractRestController<TestSubjectService> {

    public TestSubjectController() {
        super(new TestSubjectService());
    }

    /**
     * Method for searching for a cognitive test subjects.
     * <p>
     * Params are as in TestSubjectService.
     * If testId is null, then return just test subject by id.
     *
     * @return - test subject(s) for the test criteria.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestSubject> findTestSubjectsForTestCriteria(
            @RequestParam(value = "testSubjectId") long testSubjectId,
            @RequestParam(value = "testId", required = false) Long testId) {
        List<TestSubject> result;
        if (testId == null) {
            // Then return test subject with id
            result = new ArrayList<TestSubject>();
            result.add(service.findTestSubject(testSubjectId));
        } else {
            // Then return all test subjects who took the cognitive test.
            result = service.findTestSubjectsWhoParticipatedInTest(testId);
        }
        return result;
    }



    /**
     * Method for creating test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestSubject(
            @RequestParam String name,
            @RequestParam Integer ipAddress,
            @RequestParam String browser) {
        service.createTestSubject(name, ipAddress, browser);

    }

    /**
     * Method for updating test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void updateTestSubject(
            @RequestParam TestSubject subject){
        service.updateTestSubject(subject);
    }

    /**
     * Method for deleting test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTestSubject(@RequestParam long testSubjectId) {
        service.deleteTestSubject(testSubjectId);
    }

}
