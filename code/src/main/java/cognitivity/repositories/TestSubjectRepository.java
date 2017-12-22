//package cognitivity.repositories;
//
//import javax.persistence.*;
//
//import org.springframework.stereotype.Repository;
//
///**
// *
// * Data Access Object representation of a repository of cognitive test's subjects.
// *
// * This class will use hibernate to pull and push data from the sql data server.
// * This data will be a representation of a cognitive test's subject.
// *
// */
//
//@Repository
//@Entity
//@Table(name = "testSubject")
//public class TestSubjectRepository extends AbstractRepository {
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "ipAddress")
//    private Integer ipAddress;
//
//    @Column(name = "browser")
//    private String browser;
//
//	/**
//	* Returns value of name
//	* @return
//	*/
//	public String getName() {
//		return name;
//	}
//
//	/**
//	* Sets new value of name
//	* @param
//	*/
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	/**
//	* Returns value of ipAddress
//	* @return
//	*/
//	public Integer getIpAddress() {
//		return ipAddress;
//	}
//
//	/**
//	* Sets new value of ipAddress
//	* @param
//	*/
//	public void setIpAddress(Integer ipAddress) {
//		this.ipAddress = ipAddress;
//	}
//
//	/**
//	* Returns value of browser
//	* @return
//	*/
//	public String getBrowser() {
//		return browser;
//	}
//
//	/**
//	* Sets new value of browser
//	* @param
//	*/
//	public void setBrowser(String browser) {
//		this.browser = browser;
//	}
//}
