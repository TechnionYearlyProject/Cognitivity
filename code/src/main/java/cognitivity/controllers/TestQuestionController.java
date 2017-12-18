package cognitivity.controllers;

import cognitivity.dao.RepositorySearchResult;
import cognitivity.dao.TestQuestion;
import cognitivity.dto.TestQuestionDTO;
import cognitivity.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-questions")
public class TestQuestionController extends AbstractRestController<QuestionService> {

    @Autowired
    public TestQuestionController(QuestionService service) {
        super(service);
    }

    /**
     * Method for searching for cognitive test questions.
     *
     * If testId is null, then return all questions of test manager.
     *
     * @return - Cognitive test(s) for the test manager.
     * */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<TestQuestionDTO> findTestQuestionsForTestCriteriaById(
            @RequestParam(value = "testManagerId") long testManagerId,
            @RequestParam(value = "testId", required = false) Long testId) {

        if (testId == null) {
            // Then return all questions of test manager
            RepositorySearchResult<TestQuestion> result = service.findTestQuestionsForTestManagerById(testManagerId);
            return TestQuestionDTO.mapFromTestQuestionEntities(result.getResult());
        } else {
            // Then return question of that one test.
            RepositorySearchResult<TestQuestion> result = service.getTestQuestionsForTest(testId);
            return TestQuestionDTO.mapFromTestQuestionEntities(result.getResult());
        }
    }

    /**
     * Method for saving (update / create) test questions.
     *
     * Params are as in TestQuestionService.
     * If questionId is null, then create. otherwise - update.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void saveCognitiveTestQuestion(
            @RequestParam(value = "questionId", required = false) Long questionId,
            @RequestBody TestQuestionDTO question) {

        if (questionId == null) {
            service.createTestQuestion(question);
        } else {
            service.updateTestQuestion(questionId, question);
        }
    }

    /**
     * Method for delete test questions.
     *
     * Params are as in TestQuestionService.
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCognitiveTest(@RequestParam long quesstionId) {
        service.deleteTestQuestion(quesstionId);
    }

}
