package cognitivity.controllers;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.dao.TestSubject;
import cognitivity.dto.TestSubjectDTO;
import cognitivity.services.TestSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-subjects")
public class TestSubjectController extends AbstractRestController<TestSubjectService> {

    @Autowired
    public TestSubjectController(TestSubjectService service) {
        super(service);
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
    public List<TestSubjectDTO> findTestSubjectsForTestCriteria(
            @RequestParam(value = "testSubjectId") long testSubjectId,
            @RequestParam(value = "testId", required = false) Long testId) {

        if (testId == null) {
            // Then return test subject with id
            TestSubject result = service.findTestSubject(testSubjectId);
            return Collections.singletonList(TestSubjectDTO.mapFromTestSubjectEntity(result));
        } else {
            // Then return all test subjects who took the cognitive test.
            RepositorySearchResult<TestSubject> result = service.findTestSubjectsWhoParticipatedInTest(testId);
            return TestSubjectDTO.mapFromTestSubjectEntities(result.getResult());
        }
    }

    /**
     * Method for saving (update / create) test subjects.
     * <p>
     * Params are as in TestSubjectService.
     * If testSubjectId is null, then create. otherwise - update.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestSubject(
            @RequestParam(value = "testSubjectId", required = false) Long testSubjectId,
            @RequestBody TestSubjectDTO testSubject) {

        if (testSubjectId == null) {
            service.createTestSubject(testSubject);
        } else {
            service.updateTestForTestManager(testSubjectId, testSubject);
        }

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
