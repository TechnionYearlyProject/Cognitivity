package cognitivity.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by ophir on 08/12/17.
 */

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    /**
    *
    *   This method is meant for testing purposes only (create mock data), as we should not set Ids manually,
    *   Hibernate will do it automatically
    *
    */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}

