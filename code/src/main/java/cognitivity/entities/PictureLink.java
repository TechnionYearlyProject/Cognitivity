package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


/**
 * Created by peers on 15/06/2018.
 */

@Entity
@Table(name = "pictureLink")
public class PictureLink extends AbstractEntity{

    @Column(name = "link")
    private String link;

    public PictureLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
