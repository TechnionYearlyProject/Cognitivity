package cognitivity.entities;

import javax.persistence.*;

/**
 * Created by Guy on 20/1/18.
 * <p>
 * <p>
 * The Test Question persistent (JPA) representation (tables).
 */
@Entity
@Table(name = "testQuestion")
public class TestQuestion extends AbstractEntity {
    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "pictureLink")
    private String pictureLink;

    @ManyToOne
    @JoinColumn(name = "testBlockId", nullable = false)
    private TestBlock testBlock;

    //todo: do we need this columns? ( CognitiveTest, TestManager )
    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private CognitiveTest cognitiveTest;

    @ManyToOne
    @JoinColumn(name = "testManagerId")
    private TestManager testManager;

    public TestManager getTestManager() {
        return testManager;
    }

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    public TestQuestion(String question, String pictureLink, TestBlock testBlock,
                        CognitiveTest cognitiveTest, TestManager testManager) {
        this.question = question;
        this.testBlock = testBlock;
        this.cognitiveTest = cognitiveTest;
        this.testManager = testManager;
        this.pictureLink = pictureLink;
    }

    public TestQuestion() {
    }

    /**
     * Returns value of question
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets new value of question
     *
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

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getPictureLink() {

        return pictureLink;
    }
}
