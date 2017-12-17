package cognitivity.controllers;

import cognitivity.services.TestManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 23/11/17.
 */

@RestController
@RequestMapping("test-managers")
public class TestManagerController extends AbstractRestController<TestManagerService> {

    @Autowired
    public TestManagerController(TestManagerService service) {
        super(service);
    }
}
