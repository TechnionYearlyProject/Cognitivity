package cognitivity.controllers;

import cognitivity.services.TestSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-subjects")
public class TestSubjectController {

    private final TestSubjectService mTestSubjectService;

    @Autowired
    public TestSubjectController(TestSubjectService mTestSubjectService) {
        this.mTestSubjectService = mTestSubjectService;
    }
}
