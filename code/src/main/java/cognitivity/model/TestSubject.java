package cognitivity.model;

import javax.persistence.*;
/**
 *
 * The Test Subject persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testSubject")
public class TestSubject {
    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "ipAddress")
    private Integer ipAddress;

    @Column(name = "browser")
    private String browser;

	/**
	* Returns value of id
	* @return
	*/
	public Integer getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(Integer id) {
		this.id = id;
	}

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

	/**
	* Returns value of ipAddress
	* @return
	*/
	public Integer getIpAddress() {
		return ipAddress;
	}

	/**
	* Sets new value of ipAddress
	* @param
	*/
	public void setIpAddress(Integer ipAddress) {
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
