package cognitivity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 *
 * The Test Subject persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testSubject")
public class TestSubject extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "ipAddress")
    private String ipAddress;

    @Column(name = "browser")
    private String browser;

	public TestSubject(String name, String ipAddress, String browser) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.browser = browser;
	}

	public TestSubject() {}

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

<<<<<<< HEAD
=======
	/**
	* Returns value of ipAddress
	* @return
	*/
>>>>>>> 95da11194543c5cc4b0bd0a4a00f599b3939cc23
	public String getIpAddress() {
		return ipAddress;
	}

<<<<<<< HEAD
=======
	/**
	* Sets new value of ipAddress
	* @param
	*/
>>>>>>> 95da11194543c5cc4b0bd0a4a00f599b3939cc23
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	* Returns value of browser
	* @return
	*/
	public String getBrowser() {
		return browser;
	}

	/**
	* Sets new value of browser
	* @param
	*/
	public void setBrowser(String browser) {
		this.browser = browser;
	}
}
