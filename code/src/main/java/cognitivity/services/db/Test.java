package cognitivity.services.db;
import javax.persistence.*;


@Entity
@Table(name = "test")
public class Test{
    @Id @GeneratedValue
    @Column(name = "test_id")
    private Integer testId;

    @Column(name = "number_of_participators")
    private Integer numberOfParticipators;

	/**
	* Returns value of testId
	* @return
	*/
	public Integer getTestId() {
		return testId;
	}

	/**
	* Sets new value of testId
	* @param
	*/
	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	/**
	* Returns value of numberOfParticipators
	* @return
	*/
	public Integer getNumberOfParticipators() {
		return numberOfParticipators;
	}

	/**
	* Sets new value of numberOfParticipators
	* @param
	*/
	public void setNumberOfParticipators(Integer numberOfParticipators) {
		this.numberOfParticipators = numberOfParticipators;
	}
}
