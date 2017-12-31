package cognitivity.controllers;

import cognitivity.dao.TestAnswerDAO;
import cognitivity.services.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-answers")
public class TestAnswerController extends AbstractRestController<TestAnswerService> {

    @Autowired
    public TestAnswerController(TestAnswerService service) {
        super(service);
    }


    /**
     * Method for searching a test answer by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer with the given id.
     * */
    //TODO: fix!!
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(method = RequestMethod.GET)
//    public TestAnswerDAO findTestAnswerById(
//            @RequestParam(value = "testAnswerId") long answerId) {
//        TestAnswer result = service.findTestAnswerById(answerId);
//        return TestAnswerDAO.mapFromTestAnswerEntity(result);
//    }

    /**
     * Method for searching test answers by question id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the question with the given id.
     * */
    //TODO: fix!
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(method = RequestMethod.GET)
//    public List<TestAnswerDAO> findTestAnswersByQuestionId(
//            @RequestParam(value = "testQuestionId") long questionId) {
//        RepositorySearchResult<TestAnswer> result = service.findTestAnswersByQuestionId(questionId);
//        return TestAnswerDAO.mapFromCognitiveTestEntities(result.getResult());
//    }

    /**
     * Method for searching test answers for a subject by its id.
     *
     * Params are as in TestAnswerService.
     *
     * @return - test answer(s) of the subject with the given id.
     * */
    //TODO: fix!!
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(method = RequestMethod.GET)
//    public List<TestAnswerDAO> findTestAnswersBySubjectId(
//            @RequestParam(value = "testSubjectId") long subjectId) {
//        RepositorySearchResult<TestAnswer> result = service.findTestAnswersBySubjectId(subjectId);
//        return TestAnswerDAO.mapFromCognitiveTestEntities(result.getResult());
//    }

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
