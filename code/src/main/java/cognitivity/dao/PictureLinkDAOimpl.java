package cognitivity.dao;

import cognitivity.entities.PictureLink;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by peers on 15/06/2018.
 */
@Repository
@Transactional
public class PictureLinkDAOimpl extends AbstractDAO<PictureLink>  implements PictureLinkDAO {


    public long add(PictureLink link){
        Session session = sessionFactory.getCurrentSession();

        session.save(link);
        return link.getId();
    }

    public PictureLink get(Long id) { return super.get(id, PictureLink.class); }

    public void delete(Long id) {
        super.delete(id, PictureLink.class);
    }

    public List<PictureLink> getAllLinks() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from PictureLink ";
        Query<PictureLink> query = session.createQuery(queryString, PictureLink.class);
        return query.getResultList();
    }

    public void deleteLinkByName(String name){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "delete from PictureLink where name = :name";
        Query<PictureLink> query = session.createQuery(queryString, PictureLink.class);
        query.setParameter("name", name);
    }

}
