package cognitivity.model;

import javax.persistence.*;
/**
 *
 * The Test Question persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "testSubject")
public class TestQuestion extends AbstractEntity {
    @Column(name = "question", nullable = false)
    private String question; //For an open question or for all types?

    // TODO: need to talk to peer to see how we do the enums..
    @Column(name = "questionType", nullable = false)
    private Integer questionType; //An Integer? how is that enough? why not a string? how will Rekefet know what this number represents..?

    @Column(name = "answer")
    private Integer answer; //What is this??
	
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
}
