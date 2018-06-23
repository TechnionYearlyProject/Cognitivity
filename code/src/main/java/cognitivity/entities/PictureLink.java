package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


/**
 * Created by peers on 15/06/2018.
 */

@Entity
@Table(name = "pictureLinks")
public class PictureLink extends AbstractEntity{

    @Column(name = "link")
    private String link;

    @Column(name = "name")
    private String name;

    public PictureLink() {
    }

    public PictureLink(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
