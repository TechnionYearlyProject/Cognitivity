package cognitivity.controllers;

import cognitivity.dto.TestAnswerDTO;
import cognitivity.model.RepositorySearchResult;
import cognitivity.model.TestAnswer;
import cognitivity.services.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-answers")
public class TestAnswerController {

    private final TestAnswerService service;

    @Autowired
    public TestAnswerController(TestAnswerService service) {
        this.service = service;
    }


    /**
     * Method for searching a test answer by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer with the given id.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public TestAnswerDTO findTestAnswerById(
            @RequestParam(value = "testAnswerId") String answerId) {
        TestAnswer result = service.findTestAnswerById(answerId);
        return TestAnswerDTO.mapFromTestAnswerEntity(result);
    }

    /**
     * Method for searching test answers by question id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the question with the given id.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestAnswerDTO> findTestAnswersByQuestionId(
            @RequestParam(value = "testQuestionId") String questionId) {
        RepositorySearchResult<TestAnswer> result = service.findTestAnswersByQuestionId(questionId);
        return TestAnswerDTO.mapFromCognitiveTestEntities(result.getResult());
    }

    /**
     * Method for searching test answers for a subject by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the subject with the given id.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestAnswerDTO> findTestAnswersBySubjectId(
            @RequestParam(value = "testSubjectId") String subjectId) {
        RepositorySearchResult<TestAnswer> result = service.findTestAnswersBySubjectId(subjectId);
        return TestAnswerDTO.mapFromCognitiveTestEntities(result.getResult());
    }

    /**
     * Method for saving (update / create) test answers.
     *
     * Params are as in TestAnswerService.
     * If answerId == null => create.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveTestAnswer(
            @RequestParam(value = "testQuestionId") String questionId,
            @RequestParam(value = "testAnswerId", required = false) String answerId,
            @RequestBody TestAnswerDTO answerDTO) {
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
            @RequestParam(value = "testQuestionId") String questionId,
            @RequestParam(value = "testAnswerId", required = false) String answerId) {
        if (StringUtils.isEmpty(answerId)) {
            // Then delete all answers
            service.deleteAllTestAnswersForQuestion(questionId);
        } else {
            // Then delete one answer with the answer id
            service.deleteTestAnswerForQuestion(questionId, answerId);
        }
    }


    /**
     * Error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
