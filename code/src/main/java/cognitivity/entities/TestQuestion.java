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
    private String answer;

    @Column(name = "tag")
	private String tag;

	@ManyToOne
	@JoinColumn(name = "testBlockId", nullable = false)
	private TestBlock testBlock;

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

	@Column(name = "questionPosition")
	private Integer questionPosition;

    public TestQuestion(String question, Integer questionType,
                        String answer, String tag, TestBlock testBlock,
                        CognitiveTest cognitiveTest, TestManager testManager,
                        Integer questionPosition) {
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;
        this.tag = tag;
        this.testBlock = testBlock;
        this.cognitiveTest = cognitiveTest;
        this.testManager = testManager;
        this.questionPosition = questionPosition;
    }

    public TestQuestion() {}

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(Integer questionPosition) {
        this.questionPosition = questionPosition;
    }

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

	public TestBlock getTestBlock() {
		return testBlock;
	}

	public void setTestBlock(TestBlock block) {
		this.testBlock = block;
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
