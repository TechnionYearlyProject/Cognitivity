package cognitivity.model;

import javax.persistence.*;
/**
 *
 * The Test Question persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "testSubject")
public class TestQuestion {
    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "question", nullable = false)
    private String question;

    // TODO: need to talk to peer to see how we do the enums..
    @Column(name = "questionType", nullable = false)
    private Integer questionType;

    @Column(name = "answer")
    private Integer answer;

    /**
	* Returns value of id
	* @return
	*/
	public Integer getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(Integer id) {
		this.id = id;
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