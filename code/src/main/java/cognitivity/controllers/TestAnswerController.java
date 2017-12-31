package cognitivity.controllers;

import cognitivity.dao.TestAnswerDAO;
import cognitivity.entities.TestAnswer;
import cognitivity.services.TestAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("test-answers")
public class TestAnswerController extends AbstractRestController<TestAnswerService> {

    public TestAnswerController() {
        super(new TestAnswerService());
    }


    /**
     * Method for searching a test answer by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer with the given id.
     * */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public TestAnswer findTestAnswerById(
            @RequestParam(value = "testAnswerId") long answerId) {
        TestAnswer result = service.findTestAnswerById(answerId);
        return result;
    }

    /**
     * Method for searching test answers by question id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the question with the given id.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestAnswer> findTestAnswersByQuestionId(
            @RequestParam(value = "testQuestionId") long questionId) {
        List<TestAnswer> result = service.findTestAnswersByQuestionId(questionId);
        return result;
    }

    /**
     * Method for searching test answers for a subject by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the subject with the given id.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestAnswer> findTestAnswersBySubjectId(
            @RequestParam(value = "testSubjectId") long subjectId) {
        List<TestAnswer> result = service.findTestAnswersBySubjectId(subjectId);
        return result;
    }

    /**
     * Method for saving (update / create) test answers.
     *
     * Params are as in TestAnswerService.
     * If answerId == null => create.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestAnswer(
            @RequestParam(value = "testQuestionId") long questionId,
            @RequestParam(value = "testAnswerId", required = false) long answerId,
            @RequestBody TestAnswerDAO answerDTO) {
        if (StringUtils.isEmpty(answerId)) {
            // Then create
            service.addTestAnswerForTestQuestion(questionId, answerDTO);
        } else {
            // Then update
            service.updateTestAnswerForQuestion(questionId, answerId, answerDTO);
        }
    }

    /**
     * Method for deleting test answers.
     *
     * Semantics : this operation does NOT(!) delete the question!
     *
     * If answerId == null => delete all answers for questionId.
     *
     * Params are as in TestAnswerService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTestAnswer(
            @RequestParam(value = "testQuestionId") long questionId,
            @RequestParam(value = "testAnswerId", required = false) long answerId) {
        if (StringUtils.isEmpty(answerId)) {
            // Then delete all answers
            service.deleteAllTestAnswersForQuestion(questionId);
        } else {
            // Then delete one answer with the answer id
            service.deleteTestAnswerForQuestion(questionId, answerId);
        }
    }

}
