package cognitivity.controllers;

import cognitivity.services.EmailRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.EmailRegistrationController.baseMapping;

/**
 * Created by ophir on 16/01/18.
 */
@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class EmailRegistrationController extends AbstractRestController<EmailRegistrationService> {

    public static final String baseMapping = "/emails";


    @Autowired
    public EmailRegistrationController(EmailRegistrationService service) {
        super(service);
    }


    /**
     * Method for finding an id by the email.
     * <p>
     * Params are as in EmailRegistrationService.
     *
     * @return - id by the email given.
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findEmailId")
    public Long findEmailId(
            @RequestParam(value = "email") String email) {
        return service.findEmailId(email);
    }


    /**
     * Method for saving emails.
     * <p>
     * Params are as in EmailRegistrationService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/saveEmail")
    public void saveEmail(
            @RequestParam(value = "email") String email) {
        service.createEmailEntry(email);
    }

    /**
     * Method for delete email entries.
     * <p>
     * Params are as in EmailRegistrationService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteEmail")
    public void deleteEmail(@RequestParam(value = "email") String email) {
        service.deleteEmailEntry(email);
    }

}
