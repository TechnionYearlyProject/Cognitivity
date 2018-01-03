package cognitivity.controllers;

import cognitivity.dao.TestAnswerDAO;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
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
            @RequestParam TestQuestion question) {
        List<TestAnswer> result = service.findAllTestAnswerForAQuestion(question);
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
            @RequestParam TestSubject subject) {
        List<TestAnswer> result = service.findTestAnswersBySubject(subject);
        return result;
    }

    /**
     * Method for saving updating test answers.
     *
     * Params are as in TestAnswerService.
     *
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void updateTestAnswer(
            @RequestParam TestAnswer answer) {
        service.updateTestAnswerForQuestion(answer);
    }



    /**
     * Method for saving creating test answers.
     *
     * Params are as in TestAnswerService.
     *
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestAnswer(
            @RequestParam TestSubject testSubject,
            @RequestParam TestQuestion question,
            @RequestParam CognitiveTest cognitiveTest,
            @RequestParam Integer numberOfClick,
            @RequestParam Integer finalAnswer,
            @RequestParam Integer questionPlacement,
            @RequestParam Integer answerPlacement,
            @RequestParam String verbalAnswer,
            @RequestParam Boolean questionWithPicture,
            @RequestParam String timeToAnswer,
            @RequestParam Boolean timeMeasured,
            @RequestParam Boolean timeShowed,
            @RequestParam Boolean testeeExit) {
        service.addTestAnswerForTestQuestion(testSubject,question,cognitiveTest,numberOfClick,finalAnswer,
                questionPlacement,answerPlacement,verbalAnswer,questionWithPicture,timeToAnswer,timeMeasured,timeShowed,testeeExit);
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
            @RequestParam TestQuestion question,
            @RequestParam(value = "testAnswerId", required = false) long answerId) {
        if (StringUtils.isEmpty(answerId)) {
            // Then delete all answers
            service.deleteAllTestAnswersForQuestion(question);
        } else {
            // Then delete one answer with the answer id
            service.deleteTestAnswerForQuestion(answerId);
        }
    }

}
