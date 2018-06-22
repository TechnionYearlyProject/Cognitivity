package cognitivity.controllers;

import cognitivity.entities.PictureLink;
import cognitivity.exceptions.DBException;
import cognitivity.services.PictureLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cognitivity.controllers.AbstractRestController.crossOrigin;
import static cognitivity.controllers.TestSubjectController.baseMapping;

/**
 * Created by peers on 15/06/2018.
 *
 * A REST controller for all the picture links in the system.
 */

@RestController
@RequestMapping(value = baseMapping,
        produces = "application/json;charset=UTF-8")
@CrossOrigin(origins = crossOrigin)
public class PictureLinkController extends AbstractRestController<PictureLinkService> {

    public static final String baseMapping = "/picture-links";

    @Autowired
    public PictureLinkController(PictureLinkService service) {
        super(service);
    }

    /**
     * Saving in a picture link in the system.
     * @param link - The link to be saved.
     * @return - The saved picture link.
     * @throws DBException - In case of DB error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/savePictureLink")
    public PictureLink savePictureLink(@RequestBody String link) throws DBException {
        return service.createPictureLink(link);
    }


    /**
     * Getting a picture link from the system given its ID.
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
     * @param pictureLinkID - The picture link ID to be deleted.
     * @throws DBException - In case of DB error.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePictureLink")
    public void deletePictureLink(@RequestParam(value = "PictureLinkID") long pictureLinkID) throws DBException {
        service.deletePictureLink(pictureLinkID);
    }

    /**
     * Getting all picture links in the system
     * @return - All picture links in the system.
     * @throws DBException - In case of DB error.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/findAllPictureLinksInTheSystem")
    public List<PictureLink> findAllPictureLinksInTheSystem() throws DBException {
        return service.getAllPictureLinks();
    }



}
