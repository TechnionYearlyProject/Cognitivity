package cognitivity.services.db;
import javax.persistence.*;


@Entity
@Table(name = "participator")
public class Participator{
    @Id @GeneratedValue
    @Column(name = "participator_id")
    private Integer participator_id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "ip_address")
    private String ip_address;

    @Column(name = "browser")
    private String browser;

	/**
	* Returns value of participator_id
	* @return
	*/
	public Integer getParticipator_id() {
		return participator_id;
	}

	/**
	* Sets new value of participator_id
	* @param
	*/
	public void setParticipator_id(Integer participator_id) {
		this.participator_id = participator_id;
	}

	/**
	* Returns value of test
	* @return
	*/
	public Test getTest() {
		return test;
	}

	/**
	* Sets new value of test
	* @param
	*/
	public void setTest(Test test) {
		this.test = test;
	}

	/**
	* Returns value of ip_address
	* @return
	*/
	public String getIp_address() {
		return ip_address;
	}

	/**
	* Sets new value of ip_address
	* @param
	*/
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
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
