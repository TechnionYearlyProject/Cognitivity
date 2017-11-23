package cognitivity.controllers;

import cognitivity.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-questions")
public class TestQuestionController {

    private final QuestionService mQuestionService;

    @Autowired
    public TestQuestionController(QuestionService mQuestionService) {
        this.mQuestionService = mQuestionService;
    }
}
