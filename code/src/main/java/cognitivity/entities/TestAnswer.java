package cognitivity.entities;

import javax.persistence.*;

/**
 * Created by Guy on 20/1/18.
 *
 *
 * The Test Answer persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testAnswer")
public class TestAnswer extends AbstractEntity {

	@ManyToOne
    @JoinColumn(name = "testeeId", nullable = false)
    private TestSubject testSubject;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private TestQuestion question;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private CognitiveTest cognitiveTest;

    @Column(name = "finalAnswer")
	private String finalAnswer;

	public TestAnswer(TestSubject testSubject, TestQuestion question, CognitiveTest cognitiveTest,
					  String finalAnswer) {
		this.testSubject = testSubject;
		this.question = question;
		this.cognitiveTest = cognitiveTest;
		this.finalAnswer = finalAnswer;
	}

	/**
	 * Returns value of testeeAnswer
	 * @return
	 */
	public String getFinalAnswer() {
		return finalAnswer;
	}

	/**
	 * Sets new value of testSubject
	 * @param
	 */
	public void setFinalAnswer(String finalAnswer) {
		this.finalAnswer = finalAnswer;
	}

	public TestAnswer() {}

	/**
	* Returns value of testSubject
	* @return
	*/
	public TestSubject getTestSubject() {
		return testSubject;
	}

	/**
	* Sets new value of testSubject
	* @param
	*/
	public void setTestSubject(TestSubject testSubject) {
		this.testSubject = testSubject;
	}

	/**
	* Returns value of question
	* @return
	*/
	public TestQuestion getQuestion() {
		return question;
	}

	/**
	* Sets new value of question
	* @param
	*/
	public void setQuestion(TestQuestion question) {
		this.question = question;
	}

	/**
	* Returns value of cognitiveTest
	* @return
	*/
	public CognitiveTest getCognitiveTest() {
		return cognitiveTest;
	}

	/**
	* Sets new value of cognitiveTest
	* @param
	*/
	public void setCognitiveTest(CognitiveTest cognitiveTest) {
		this.cognitiveTest = cognitiveTest;
	}
}
