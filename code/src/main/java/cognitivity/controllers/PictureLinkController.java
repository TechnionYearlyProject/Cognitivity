package cognitivity.controllers;

import cognitivity.entities.PictureLink;
import cognitivity.exceptions.DBException;
import cognitivity.services.PictureLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.PictureLinkController.baseMapping;

/**
 * Created by peers on 15/06/2018.
 * <p>
 * A REST controller for all the picture links in the system.
 */

@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://cognitivityfrontend.azurewebsites.net"
})
public class PictureLinkController extends AbstractRestController<PictureLinkService> {

    public static final String baseMapping = "/picture-links";

    @Autowired
    public PictureLinkController(PictureLinkService service) {
        super(service);
    }

    /**
     * Saving in a picture link in the system.
     *
     * @param link - The link to be saved.
     * @return - The saved picture link.
     * @throws DBException - In case of DB error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/savePictureLink")
    public void savePictureLink(@RequestBody PictureLink link) throws DBException {
        service.createPictureLink(link);
    }


    /**
     * Getting a picture link from the system given its ID.
     *
     * @param linkID - The picture link ID.
     * @return - The picture link if it exists in the system, null if not.
     * @throws DBException - In case of DB error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findPictureLinkById")
    public PictureLink findPictureLinkById(
            @RequestParam(value = "linkID") long linkID) throws DBException {
        return service.findPictureLinkById(linkID);
    }

    /**
     * Updating a picture link.
     *
     * @param link - The link to be updated.
     * @throws DBException - In case of DB error.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/updatePictureLink")
    public void updatePictureLink(
            @RequestBody PictureLink link) throws DBException {
        applicationInsights.trackEvent("UpdatePictureLink");
        service.updatePictureLink(link);
    }

    /**
     * Deleting a picture link from the system.
     *
     * @param PictureLinkName - The picture link name to be deleted.
     * @throws DBException - In case of DB error.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePictureLink")
    public void deletePictureLink(@RequestParam(value = "PictureLinkName") String PictureLinkName) throws DBException {
        service.deletePictureLink(PictureLinkName);
    }

    /**
     * Getting all picture links in the system
     *
     * @return - All picture links in the system.
     * @throws DBException - In case of DB error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findAllPictureLinksInTheSystem")
    public List<String> findAllPictureLinksInTheSystem() throws DBException {
        return service.getAllPictureLinks();
    }

}
