package cognitivity.dto;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import com.sun.istack.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for test blocks.
 * Including the List of the questions they hold
 */
public class BlockWrapper {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Boolean getRandomize() {
        return randomize;
    }

    public void setRandomize(Boolean randomize) {
        this.randomize = randomize;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CognitiveTest getCognitiveTest() {
        return cognitiveTest;
    }

    public void setCognitiveTest(CognitiveTest cognitiveTest) {
        this.cognitiveTest = cognitiveTest;
    }

    public void setQuestions(List<TestQuestion> questions) {
        this.questions = questions;
    }

    private Integer numberOfQuestions;
    private Boolean randomize;
    private String tag;
    private CognitiveTest cognitiveTest;

    private List<TestQuestion> questions;

    public BlockWrapper() {}

    //TODO: remove!! hardcoded value!
    public BlockWrapper(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test) {
        this.numberOfQuestions = numberOfQuestions;
        this.randomize = randomize;
        this.tag = tag;
        this.cognitiveTest = test;

        this.questions = new ArrayList<>();
    }

    public BlockWrapper(List<TestQuestion> questions, Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test) {
        this.numberOfQuestions = numberOfQuestions;
        this.randomize = randomize;
        this.tag = tag;
        this.cognitiveTest = test;

        this.questions = questions;
    }

    public BlockWrapper(@Nullable List<TestQuestion> questions, TestBlock block) {
        this.id = block.getId();
        this.numberOfQuestions = block.getNumberOfQuestions();
        this.randomize = block.getRandomize();
        this.tag = block.getTag();
        this.cognitiveTest = block.getCognitiveTest();

        this.questions = questions != null ? questions : new ArrayList<>();
    }

    // todo : initialize id
    public BlockWrapper(TestBlock block) {
        this.id = block.getId();
        this.numberOfQuestions = block.getNumberOfQuestions();
        this.randomize = block.getRandomize();
        this.tag = block.getTag();
        this.cognitiveTest = block.getCognitiveTest();

        this.questions = new ArrayList<>();
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

    public TestBlock innerBlock(long id) {
        cognitiveTest.setId(id);
        TestBlock block = new  TestBlock(numberOfQuestions, randomize, tag, cognitiveTest);
        block.setId(this.id);
        return block;
    }

}
