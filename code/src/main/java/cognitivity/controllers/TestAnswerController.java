package cognitivity.controllers;

import cognitivity.entities.TestAnswer;
import cognitivity.exceptions.DBException;
import cognitivity.services.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.TestAnswerController.baseMapping;


@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class TestAnswerController extends AbstractRestController<TestAnswerService> {

    public static final String baseMapping = "/test-answers";

    @Autowired
    public TestAnswerController(TestAnswerService service) {
        super(service);
    }


    /**
     * Method for searching a test answer by its id.
     * <p>
     * Params are as in TestAnswerService.
     *
     * @return - test answer with the given id.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestAnswerById")
    public TestAnswer findTestAnswerById(
            @RequestParam(value = "testAnswerId") long answerId) {
        return service.findTestAnswerById(answerId);
    }

    /**
     * Method for searching test answers by question id.
     * <p>
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the question with the given id.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestAnswersByQuestionId")
    public List<TestAnswer> findTestAnswersByQuestionId(
            @RequestParam(value = "questionId") long questionId) {
        return service.findAllTestAnswerForAQuestion(questionId);
    }

    /**
     * Method for searching test answers for a subject by its id.
     * <p>
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the subject with the given id.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findTestAnswersBySubjectId")
    public List<TestAnswer> findTestAnswersBySubjectId(
            @RequestParam(value = "subjectId") long subjectId) {
        return service.findTestAnswersBySubject(subjectId);
    }

    /**
     * Method for saving updating test answers.
     * <p>
     * Params are as in TestAnswerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updateTestAnswer")
    public void updateTestAnswer(
            @RequestBody TestAnswer answer) throws DBException {
        service.updateTestAnswerForQuestion(answer);
    }


    /**
     * Method for saving creating test answers.
     * <p>
     * Params are as in TestAnswerService.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestAnswer")
    public TestAnswer saveTestAnswer(
            @RequestBody TestAnswer testAnswer) throws DBException {
        return service.addTestAnswerForTestQuestion(testAnswer);
    }

    /**
     * Method for deleting test answers.
     * <p>
     * Semantics : this operation does NOT(!) delete the question!
     * <p>
     * If answerId == -1 => delete all answers for questionId.
     * <p>
     * Params are as in TestAnswerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTestAnswer")
    public void deleteTestAnswer(
            @RequestParam(value = "questionId") long questionId,
            @RequestParam(value = "testAnswerId", required = false) long answerId) throws DBException {
        if (answerId == -1) {
            // Then delete all answers
            service.deleteAllTestAnswersForQuestion(questionId);
        } else {
            // Then delete one answer with the answer id
            service.deleteTestAnswerForQuestion(answerId);
        }
    }

}
