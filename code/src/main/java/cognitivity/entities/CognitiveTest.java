package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Guy on 20/1/18.
 *
 *
 * The Cognitive Test persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "test")
public class CognitiveTest extends AbstractEntity {

	public CognitiveTest(String name, TestManager manager, Integer numberOfQuestions, String notes, String project) {
		this.name = name;
		this.testManager = manager;
		this.numberOfQuestions = numberOfQuestions;
		this.notes = notes;
		this.project = project;
	}

	public CognitiveTest() {}

	@Column(name = "name")
    private String name;

	@Column(name = "notes")
	private String notes;

	@Column(name = "project")
	private String project;

    @ManyToOne
    @JoinColumn(name = "managerId", nullable = false)
    @JsonIgnore
    private TestManager testManager;

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

	public String getNotes() {
		return notes;
	}

	public String getProject() {
		return project;
	}
}
