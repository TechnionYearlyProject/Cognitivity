package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * The Cognitive Test persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "project")
public class CognitiveTest extends AbstractEntity {

    public CognitiveTest(String name, TestManager manager, Integer state, Integer numberOfQuestions) {
		this.name = name;
		this.testManager = manager;
		this.numberOfSubjects = 0;
		this.state = state;
		this.lastModified = new Date(Calendar.getInstance().getTimeInMillis()); //TODO:Check for format
		this.lastAnswered = null;
		this.numberOfFiledCopies = 0;
		this.numberOfQuestions = numberOfQuestions;
	}

	public CognitiveTest() {}

	@Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "managerId", nullable = false)
    @JsonIgnore
    private TestManager testManager;

    @Column(name = "numberOfSubjects", nullable = false)
    private Integer numberOfSubjects;

    @Column(name = "state", nullable = false)
    private Integer state;

    @Column(name = "lastModified", nullable = false)
    private Date lastModified;

    @Column(name = "lastAnswered")
    private Date lastAnswered;

    @Column(name = "numberOfFiledCopies", nullable = false)
    private Integer numberOfFiledCopies;

    @Column(name = "numberOfQuestions", nullable = false)
    private Integer numberOfQuestions;


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
	* Returns value of manager
	* @return
	*/
	public TestManager getManager() {
		return testManager;
	}

	/**
	* Sets new value of manager
	* @param
	*/
	public void setManager(TestManager manager) {
		this.testManager = manager;
	}

	/**
	* Returns value of numberOfTestees
	* @return
	*/
	public Integer getNumberOfSubjects() {
		return numberOfSubjects;
	}

	/**
	* Sets new value of numberOfTestees
	* @param
	*/
	public void setNumberOfSubjects(Integer numberOfSubjects) {
		this.numberOfSubjects = numberOfSubjects;
	}

	/**
	* Returns value of state
	* @return
	*/
	public Integer getState() {
		return state;
	}

	/**
	* Sets new value of state
	* @param
	*/
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	* Returns value of lastModified
	* @return
	*/
	public Date getLastModified() {
		return lastModified;
	}

	/**
	* Sets new value of lastModified
	* @param
	*/
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	* Returns value of lastAnswered
	* @return
	*/
	public Date getLastAnswered() {
		return lastAnswered;
	}

	/**
	* Sets new value of lastAnswered
	* @param
	*/
	public void setLastAnswered(Date lastAnswered) {
		this.lastAnswered = lastAnswered;
	}

	/**
	* Returns value of numberOfFiledCopies
	* @return
	*/
	public Integer getNumberOfFiledCopies() {
		return numberOfFiledCopies;
	}

	/**
	* Sets new value of numberOfFiledCopies
	* @param
	*/
	public void setNumberOfFiledCopies(Integer numberOfFiledCopies) {
		this.numberOfFiledCopies = numberOfFiledCopies;
	}

	/**
	* Returns value of numberOfQuestions
	* @return
	*/
	public Integer getNumberOfQuestions() {
		return numberOfQuestions;
	}

	/**
	* Sets new value of numberOfQuestions
	* @param
	*/
	public void setNumberOfQuestions(Integer numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
}
