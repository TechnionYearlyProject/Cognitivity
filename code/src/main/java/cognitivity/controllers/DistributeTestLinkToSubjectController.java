package cognitivity.controllers;

import cognitivity.entities.TestSubject;
import cognitivity.exceptions.SendLinksException;
import cognitivity.services.DistributeTestLinkToSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.DistributeTestLinkToSubjectController.baseMapping;

/**
 * REST service that allows sending test links to subjects.
 * <p>
 * Created by ophir on 26/5/18.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class DistributeTestLinkToSubjectController extends AbstractRestController<DistributeTestLinkToSubjectService> {

    public static final String baseMapping = "/send-links";


    @Autowired
    public DistributeTestLinkToSubjectController(DistributeTestLinkToSubjectService service) {
        super(service);
    }


    /**
     * Method for sending the links to a list of subjects
     * <p>
     * Params are as in DistributeTestLinkToSubjectService.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/sendLinksToSubjects")
    public void sendLinksToSubjects(@RequestBody List<TestSubject> subjects,
                                    @RequestParam(value = "link") String link) throws SendLinksException {
        service.sendLinksToSubjects(subjects, link);
    }
}
