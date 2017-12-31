package cognitivity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
