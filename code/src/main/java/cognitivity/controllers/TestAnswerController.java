package cognitivity.controllers;

import cognitivity.services.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-answers")
public class TestAnswerController {

    private final TestAnswerService mTestAnswerService;

    @Autowired
    public TestAnswerController(TestAnswerService mTestAnswerService) {
        this.mTestAnswerService = mTestAnswerService;
    }
}
