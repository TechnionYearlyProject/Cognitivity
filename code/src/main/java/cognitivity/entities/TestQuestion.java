package cognitivity.entities;

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

	@ManyToOne
	@JoinColumn(name = "testBlockId", nullable = false)
	private TestBlock testBlock;

	@ManyToOne
	@JoinColumn(name = "testId", nullable = false)
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

    public TestQuestion(String question, TestBlock testBlock,
                        CognitiveTest cognitiveTest, TestManager testManager) {
        this.question = question;
        this.testBlock = testBlock;
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

	public TestBlock getTestBlock() {
		return testBlock;
	}

	public void setTestBlock(TestBlock block) {
		this.testBlock = block;
	}

	public CognitiveTest getCognitiveTest() {
		return cognitiveTest;
	}

	public void setCognitiveTest(CognitiveTest project) {
		this.cognitiveTest = project;
	}
}
