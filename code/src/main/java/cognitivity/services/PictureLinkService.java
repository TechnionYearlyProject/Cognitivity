package cognitivity.services;

import cognitivity.dao.PictureLinkDAO;
import cognitivity.entities.PictureLink;
import cognitivity.exceptions.DBException;
import cognitivity.exceptions.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by peers on 15/06/2018.
 */

@Service
public class PictureLinkService {

    private final static Logger logger = Logger.getLogger(PictureLinkService.class);

    private PictureLinkDAO dao;

    @Autowired
    public PictureLinkService(PictureLinkDAO dao) {
        this.dao = dao;
    }


    /**
     * Saving a new picture link in the system
     * @param link - The link to save
     * @return - The created picture Link
     * @throws DBException - In case of DB error.
     */
    public PictureLink createPictureLink(String link) throws DBException{
        try {
            PictureLink res = new PictureLink(link);
            dao.add(res);
            logger.info("Successfully added PictureLink. PictureLinkID: " + res.getId());
            return res;
        } catch (org.hibernate.HibernateException e) {
            logger.info("Failed to add PictureLink.",e);
            throw new DBException(ErrorType.SAVE, null);
        }
    }

    /**
     * A method for getting a picture link by Id.
     * @param id - The id of the requested picture link.
     * @return - The requested picture link if it exists, null if not.
     * @throws DBException - In case of DB error.
     */
    public PictureLink findPictureLinkById(long id)throws DBException {
        try{
            PictureLink toReturn = dao.get(id);
            if (toReturn == null){
                logger.error("Failed to find a pictureLink.Picture links with PictureLinkID: "+id+" doesn't exist");
                throw new DBException(ErrorType.DOESNT_EXIST, id);
            }
            logger.info("Successfully found a PictureLink. PictureLinkID: " + id);
            return toReturn;
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to find a PictureLink. PictureLinkID: "+id,e);
            throw new DBException(ErrorType.GET, id);
        }
    }

    /**
     * Update a picture link in the DB.
     * @param link - the updated link.
     * @throws DBException - In case of DB error.
     */
    public void updatePictureLink(PictureLink link)throws DBException {
        try{
            dao.update(link);
            logger.info("Successfully updated PictureLink. PictureLinkID: " + link.getId());
        }catch (org.hibernate.HibernateException e){
            logger.error("Failed to update a PictureLink. PictureLinkID: " + link.getId(),e);
            throw new DBException(ErrorType.UPDATE, link.getId());
        }
    }


    /**
     * Delete a picture link from the system.
     * @param linkId - the Link OID to be deleted.
     * @throws DBException - In case of DB error.
     */
    public void deletePictureLink(long linkId) throws DBException {
        try {
            dao.delete(linkId);
            logger.info("Successfully deleted PictureLink. PictureLinkID: " + linkId);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Failed to delete a PictureLink. PictureLinkID: " + linkId,e);
            throw new DBException(ErrorType.DELETE, linkId);
        }
    }

    /**
     * A method to get all picture links from the system.
     * @return - All picture links in the system.
     * @throws DBException - In case of DB error.
     */
    public List<PictureLink> getAllPictureLinks() throws DBException{
        try{
            List<PictureLink> toReturn = dao.getAllLinks();
            logger.info("Successfully got all PictureLinks.");
            return toReturn;
        }catch (org.hibernate.HibernateException e) {
            logger.error("Failed to get all PictureLinks.");
            throw new DBException(ErrorType.DELETE, null);
        }
    }
}
