package cognitivity.entities;

import javax.persistence.*;

/**
 * Created by Guy on 20/1/18.
 *
 *
 * The Test Block persistent (JPA) representation (tables).
 *
 */
@Entity
@Table(name = "testBlock")
public class TestBlock extends AbstractEntity {

    @Column(name = "numberOfQuestions", nullable = false)
    private Integer numberOfQuestions;

    @Column(name = "randomize")
    private Boolean randomize;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private CognitiveTest cognitiveTest;

    public TestBlock(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest cognitiveTest) {
        this.numberOfQuestions = numberOfQuestions;
        this.randomize = randomize;
        this.tag = tag;
        this.cognitiveTest = cognitiveTest;
    }

    public TestBlock() {}

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

    public CognitiveTest getCognitiveTest() {
        return cognitiveTest;
    }

    public void setCognitiveTest(CognitiveTest test) {
        this.cognitiveTest = test;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
