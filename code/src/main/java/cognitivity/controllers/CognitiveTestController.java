package cognitivity.controllers;

import cognitivity.services.CognitiveTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("tests")
public class CognitiveTestController {

    private final CognitiveTestService mCognitiveTestService;

    @Autowired
    public CognitiveTestController(CognitiveTestService mCognitiveTestService) {
        this.mCognitiveTestService = mCognitiveTestService;
    }
}
