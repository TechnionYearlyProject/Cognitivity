package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 *
 * The Test Question persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "testQuestion")
public class TestQuestion extends AbstractEntity {
    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "questionType", nullable = false)
    private Integer questionType;

    @Column(name = "answer")
    private Integer answer;

    @Column(name = "tag")
	private String tag;

	@ManyToOne
	@JoinColumn(name = "testBlockId", nullable = false)
	private TestBlock block;

	@ManyToOne
	@JoinColumn(name = "projectId", nullable = false)
    @JsonIgnore
    private CognitiveTest cognitiveTest;

	public TestManager getTestManager() {
		return testManager;
	}

	public void setTestManager(TestManager testManager) {
		this.testManager = testManager;
	}

	@ManyToOne
	@JoinColumn(name = "testManagerId")
	private TestManager testManager;

	public TestQuestion(String question, Integer questionType, Integer answer, String tag,
						TestBlock block, CognitiveTest cognitiveTest, TestManager testManager) {
		this.question = question;
		this.questionType = questionType;
		this.answer = answer;
		this.tag = tag;
		this.block = block;
		this.cognitiveTest = cognitiveTest;
		this.testManager = testManager;
	}

	public TestQuestion() {}

	/**
	* Returns value of question
	* @return
	*/
	public String getQuestion() {
		return question;
	}

	/**
	* Sets new value of question
	* @param
	*/
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	* Returns value of questionType
	* @return
	*/
	public Integer getQuestionType() {
		return questionType;
	}

	/**
	* Sets new value of questionType
	* @param
	*/
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	/**
	* Returns value of answer
	* @return
	*/
	public Integer getAnswer() {
		return answer;
	}

	/**
	* Sets new value of answer
	* @param
	*/
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public TestBlock getBlock() {
		return block;
	}

	public void setBlock(TestBlock block) {
		this.block = block;
	}

	public String getTag(){
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public CognitiveTest getCognitiveTest() {
		return cognitiveTest;
	}

	public void setCognitiveTest(CognitiveTest project) {
		this.cognitiveTest = project;
	}
}
