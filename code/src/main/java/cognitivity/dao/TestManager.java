package cognitivity.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 *
 * The Test Manager persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testManager")
public class TestManager extends AbstractEntity {


    @Column(name = "name")
    private String name;

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}
}
