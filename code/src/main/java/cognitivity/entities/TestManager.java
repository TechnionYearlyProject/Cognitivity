package cognitivity.entities;

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

<<<<<<< HEAD
    @Column(name = "email", unique = true)
	private String email;
=======
    @Column(name = "email")
    private String email;
>>>>>>> 95da11194543c5cc4b0bd0a4a00f599b3939cc23

	public TestManager(String email) {
		this.email = email;
	}

<<<<<<< HEAD
	/**
	 * Returns value of email
	 */
	public String getEmail() {

		return email;
	}

	/**
	 * Sets value of email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public TestManager() {}

=======
	public TestManager() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
>>>>>>> 95da11194543c5cc4b0bd0a4a00f599b3939cc23
}
