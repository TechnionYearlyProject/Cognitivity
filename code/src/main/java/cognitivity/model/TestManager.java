package cognitivity.model;

import javax.persistence.*;
/**
 *
 * The Test Manager persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testManager")
public class TestManager extends AbstractEntity {

	/*
	* Cool. Maybe we will add stuff later (who knows what Rekefet needs...)
	* */


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
