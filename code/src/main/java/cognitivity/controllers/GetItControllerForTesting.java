package cognitivity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ophir on 15/01/18.
 */

@RestController
public class GetItControllerForTesting {

    @RequestMapping(method = RequestMethod.GET, value = "/getit")
    public String getit(
            @RequestParam(value = "text") String text) {
        return "Hello " + text;
    }
}
