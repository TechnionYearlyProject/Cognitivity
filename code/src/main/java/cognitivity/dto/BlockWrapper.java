package cognitivity.dto;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;

import java.util.List;

/**
 * A wrapper class for test blocks.
 * Including the List of the questions they hold
 */
public class BlockWrapper extends TestBlock {

    private List<TestQuestion> questions;

    public BlockWrapper() {}
    public BlockWrapper(Integer numberOfQuestions, Boolean randomize, String tag, CognitiveTest test) {
        super(numberOfQuestions, randomize, tag, test);
    }

    public BlockWrapper(List<TestQuestion> questions, TestBlock block) {
        this.questions = questions;
        super.setCognitiveTest(block.getCognitiveTest());
        super.setNumberOfQuestions(block.getNumberOfQuestions());
        super.setRandomize(block.getRandomize());
        super.setTag(block.getTag());
        super.setId(block.getId());
    }

    public BlockWrapper(TestBlock block) {
        super.setCognitiveTest(block.getCognitiveTest());
        super.setNumberOfQuestions(block.getNumberOfQuestions());
        super.setRandomize(block.getRandomize());
        super.setTag(block.getTag());
        super.setId(block.getId());
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

}
