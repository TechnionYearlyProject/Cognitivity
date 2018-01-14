package cognitivity.controllers;

import cognitivity.entities.TestAnswer;
import cognitivity.services.TestAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(TestAnswerController.baseMapping)
public class TestAnswerController extends AbstractRestController<TestAnswerService> {

    public static final String baseMapping = "/test-answers";

    public TestAnswerController() {
        super(new TestAnswerService());
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
            @RequestBody TestAnswer answer) {
        service.updateTestAnswerForQuestion(answer);
    }


    /**
     * Method for saving creating test answers.
     * <p>
     * Params are as in TestAnswerService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveTestAnswer")
    public void saveTestAnswer(
            @RequestBody TestAnswer testAnswer) {
            /*@RequestParam TestSubject testSubject,
            @RequestParam TestQuestion question,
            @RequestParam CognitiveTest cognitiveTest,
            @RequestParam Integer numberOfClick,
            @RequestParam Integer finalAnswer,
            @RequestParam Integer questionPlacement,
            @RequestParam Integer answerPlacement,
            @RequestParam String verbalAnswer,
            @RequestParam Boolean questionWithPicture,
            @RequestParam Integer timeToAnswer,
            @RequestParam Boolean timeMeasured,
            @RequestParam Boolean timeShowed,
            @RequestParam Boolean testeeExit) {*/
        /*service.addTestAnswerForTestQuestion(testSubject, question, cognitiveTest, numberOfClick, finalAnswer,
                questionPlacement, answerPlacement, verbalAnswer, questionWithPicture, timeToAnswer, timeMeasured, timeShowed, testeeExit);*/
        // Todo : need to fix this - only pass TestAnswer to service
        service.addTestAnswerForTestQuestion(testAnswer);
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
            @RequestParam(value = "testAnswerId", required = false) long answerId) {
        if (answerId == -1) {
            // Then delete all answers
            service.deleteAllTestAnswersForQuestion(questionId);
        } else {
            // Then delete one answer with the answer id
            service.deleteTestAnswerForQuestion(answerId);
        }
    }

}
