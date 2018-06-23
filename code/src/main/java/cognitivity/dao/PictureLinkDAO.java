package cognitivity.dao;


import cognitivity.entities.PictureLink;

import java.util.List;

/**
 * Created by peers on 15/06/2018.
 */
public interface PictureLinkDAO {

    public PictureLink get(Long id);
    public void delete(Long id);
    public long add(PictureLink data);
    public long update(PictureLink data);

    /**
     * Get all picture links in the system.
     * @return
     */
    public List<PictureLink> getAllLinks();

    /**
     * Delete a picture link by its name
     * @param name - The name of the picture
     */
    public void deleteLinkByName(String name);

}
