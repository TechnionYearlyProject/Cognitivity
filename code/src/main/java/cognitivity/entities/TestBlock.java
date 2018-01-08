package cognitivity.entities;

import javax.persistence.*;

/**
 *
 * The Test Block persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "questionBlock")
public class TestBlock extends AbstractEntity {

    @Column(name = "numberOfQuestions", nullable = false)
    private Integer numberOfQuestions;

    @Column(name = "randomize")
    private Boolean randomize;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "id")
    private CognitiveTest test;

    public TestBlock(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test) {
        this.numberOfQuestions = numberOfQuestions;
        this.randomize = randomize;
        this.tag = tag;
        this.test = test;
    }


    /**
     * Returns the number of questions
     * @return
     */
    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    /**
     * Sets new value for number of questions
     * @param
     */
    public void setNumberOfQuestions(Integer number) {
        this.numberOfQuestions = number;
    }

    /**
     * Returns value of Randomized
     * @return
     */
    public Boolean getRandomize() {
        return randomize;
    }

    /**
     * Sets new value of Randomized
     * @param
     */
    public void setRandomize(Boolean randomize) {
        this.randomize = randomize;
    }

    public CognitiveTest getTest() {
        return test;
    }

    public void setTest(CognitiveTest test) {
        this.test = test;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
