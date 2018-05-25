package cognitivity.controllers;

import cognitivity.entities.TestSubject;
import cognitivity.exceptions.DBException;
import cognitivity.services.TestSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.TestSubjectController.baseMapping;

/**
 * REST service for Test Subject - all the information of the subject that is being saved is accessible via this
 * controller.
 * <p>
 * Created by ophir on 17/12/17.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class TestSubjectController extends AbstractRestController<TestSubjectService> {

    public static final String baseMapping = "/test-subjects";

    @Autowired
    public TestSubjectController(TestSubjectService service) {
        super(service);
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
            @RequestParam(value = "testId", required = false) long testId) throws DBException {
        applicationInsights.trackEvent("FindTestSubjectsForTestCriteria");
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
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestSubject")
    public TestSubject saveTestSubject(@RequestBody TestSubject testSubject) throws DBException {
        applicationInsights.trackEvent("SaveTestSubject");
        return service.createTestSubject(testSubject);
    }

    /**
     * Method for updating test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateTestSubject")
    public void updateTestSubject(
            @RequestBody TestSubject subject) throws DBException {
        applicationInsights.trackEvent("UpdateTestSubject");
        service.updateTestSubject(subject);
    }

    /**
     * Method for deleting test subjects.
     * <p>
     * Params are as in TestSubjectService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTestSubject")
    public void deleteTestSubject(@RequestParam(value = "testSubjectId") long testSubjectId) throws DBException {
        applicationInsights.trackEvent("DeleteTestSubject");
        service.deleteTestSubject(testSubjectId);
    }

    /**
     * Method for getting all test subjects in the system.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findAllTestSubjectsInTheSystem")
    public List<TestSubject> findAllTestSubjectsInTheSystem() throws DBException {
        applicationInsights.trackEvent("FindAllTestSubjectsInTheSystem");
        return service.findAllTestSubjectsInTheSystem();
    }

}
