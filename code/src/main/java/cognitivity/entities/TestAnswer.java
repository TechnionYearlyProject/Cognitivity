package cognitivity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
/**
 *
 * The Test Answer persistent (JPA) representation (tables).
 *
 */

@Entity
@Table(name = "testAnswer")
public class TestAnswer extends AbstractEntity {

	@ManyToOne
    @JoinColumn(name = "testeeId", nullable = false)
    @JsonIgnore
    private TestSubject testSubject;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    @JsonIgnore
    private TestQuestion question;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    @JsonIgnore
    private CognitiveTest cognitiveTest;

    @Column(name = "numberOfClick", nullable = false)
    private Integer numberOfClick;

    @Column(name = "finalAnswer")
    private Integer finalAnswer;

    @Column(name = "questionPlacement", nullable = false)
    private Integer questionPlacement;

    @Column(name = "answerPlacement", nullable = false)
    private Integer answerPlacement;

    @Column(name = "verbalAnswer")
    private String verbalAnswer;

    @Column(name = "questionWithPicture", nullable = false)
    private Boolean questionWithPicture;

    @Column(name = "timeToAnswer", nullable = false)
    private Integer timeToAnswer;

    @Column(name = "timeMeasured")
    private Boolean timeMeasured;

    @Column(name = "timeShowed")
    private Boolean timeShowed;

    @Column(name = "testeeExit")
    private Boolean testeeExit;

	public TestAnswer(TestSubject testSubject, TestQuestion question, CognitiveTest cognitiveTest, Integer numberOfClick,
					  Integer finalAnswer, Integer questionPlacement, Integer answerPlacement, String verbalAnswer,
					  Boolean questionWithPicture, Integer timeToAnswer, Boolean timeMeasured, Boolean timeShowed,
					  Boolean testeeExit) {
		this.testSubject = testSubject;
		this.question = question;
		this.cognitiveTest = cognitiveTest;
		this.numberOfClick = numberOfClick;
		this.finalAnswer = finalAnswer;
		this.questionPlacement = questionPlacement;
		this.answerPlacement = answerPlacement;
		this.verbalAnswer = verbalAnswer;
		this.questionWithPicture = questionWithPicture;
		this.timeToAnswer = timeToAnswer;
		this.timeMeasured = timeMeasured;
		this.timeShowed = timeShowed;
		this.testeeExit = testeeExit;
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

	/**
	* Returns value of numberOfClick
	* @return
	*/
	public Integer getNumberOfClick() {
		return numberOfClick;
	}

	/**
	* Sets new value of numberOfClick
	* @param
	*/
	public void setNumberOfClick(Integer numberOfClick) {
		this.numberOfClick = numberOfClick;
	}

	/**
	* Returns value of finalAnswer
	* @return
	*/
	public Integer getFinalAnswer() {
		return finalAnswer;
	}

	/**
	* Sets new value of finalAnswer
	* @param
	*/
	public void setFinalAnswer(Integer finalAnswer) {
		this.finalAnswer = finalAnswer;
	}

	/**
	* Returns value of questionPlacement
	* @return
	*/
	public Integer getQuestionPlacement() {
		return questionPlacement;
	}

	/**
	* Sets new value of questionPlacement
	* @param
	*/
	public void setQuestionPlacement(Integer questionPlacement) {
		this.questionPlacement = questionPlacement;
	}

	/**
	* Returns value of answerPlacement
	* @return
	*/
	public Integer getAnswerPlacement() {
		return answerPlacement;
	}

	/**
	* Sets new value of answerPlacement
	* @param
	*/
	public void setAnswerPlacement(Integer answerPlacement) {
		this.answerPlacement = answerPlacement;
	}

	/**
	* Returns value of verbalAnswer
	* @return
	*/
	public String getVerbalAnswer() {
		return verbalAnswer;
	}

	/**
	* Sets new value of verbalAnswer
	* @param
	*/
	public void setVerbalAnswer(String verbalAnswer) {
		this.verbalAnswer = verbalAnswer;
	}

	/**
	* Returns value of questionWithPicture
	* @return
	*/
	public Boolean getQuestionWithPicture() {
		return questionWithPicture;
	}

	/**
	* Sets new value of questionWithPicture
	* @param
	*/
	public void setQuestionWithPicture(Boolean questionWithPicture) {
		this.questionWithPicture = questionWithPicture;
	}

	/**
	* Returns value of timeToAnswer
	* @return
	*/
	public Integer getTimeToAnswer() {
		return timeToAnswer;
	}

	/**
	* Sets new value of timeToAnswer
	* @param
	*/
	public void setTimeToAnswer(Integer timeToAnswer) {
		this.timeToAnswer = timeToAnswer;
	}

	/**
	* Returns value of timeMeasured
	* @return
	*/
	public Boolean getTimeMeasured() {
		return timeMeasured;
	}

	/**
	* Sets new value of timeMeasured
	* @param
	*/
	public void setTimeMeasured(Boolean timeMeasured) {
		this.timeMeasured = timeMeasured;
	}

	/**
	* Returns value of timeShowed
	* @return
	*/
	public Boolean getTimeShowed() {
		return timeShowed;
	}

	/**
	* Sets new value of timeShowed
	* @param
	*/
	public void setTimeShowed(Boolean timeShowed) {
		this.timeShowed = timeShowed;
	}

	/**
	* Returns value of testeeExit
	* @return
	*/
	public Boolean getTesteeExit() {
		return testeeExit;
	}

	/**
	* Sets new value of testeeExit
	* @param
	*/
	public void setTesteeExit(Boolean testeeExit) {
		this.testeeExit = testeeExit;
	}
}
