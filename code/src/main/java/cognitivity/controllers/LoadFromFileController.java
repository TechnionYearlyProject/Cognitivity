package cognitivity.controllers;

import cognitivity.exceptions.DBException;
import cognitivity.exceptions.LoaderException;
import cognitivity.services.LoadFromFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cognitivity.controllers.LoadFromFileController.baseMapping;

/**
 * REST service for Loading tests from json files.
 * <p>
 * Created by ophir on 12/05/18.
 */

@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https/cognitivityfrontend.azurewebsites.net"
})
public class LoadFromFileController extends AbstractRestController<LoadFromFileService> {

    public static final String baseMapping = "/load-from-file";

    @Autowired
    public LoadFromFileController(LoadFromFileService service) {
        super(service);
    }


    /**
     * Method for loading a test from a json file.
     * <p>
     * Params are as in LoadFromFileService.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/loadFromJSONFile")
    public void loadFromJSONFile(@RequestParam(value = "managerId") long managerId,
                                 @RequestBody String jsonData) throws LoaderException, DBException {
        applicationInsights.trackEvent("LoadFromJSONFile");
        service.loadFromJSONFile(jsonData, managerId);
    }


    /**
     * Method for loading a test from a json file.
     * <p>
     * Params are as in LoadFromFileService.
     */
    /*@ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/loadTestFromDirectory")
    public void loadTestFromDirectory(@RequestParam(value = "dirName") String dirName,
                                      @RequestParam(value = "managerId") long managerId) throws LoaderException, DBException {
        applicationInsights.trackEvent("LoadTestFromDirectory");
        service.loadTestFromDirectory(dirName, managerId);
    }*/
}
