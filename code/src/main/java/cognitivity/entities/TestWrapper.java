package cognitivity.entities;


import java.util.ArrayList;
import java.util.List;


/**
 * A wrapper class For cognitive test.
 * This class includes the wrapper blocks of the blocks in the test.
 */
public class TestWrapper extends CognitiveTest {
    private List<BlockWrapper>  blocks;


    public TestWrapper(CognitiveTest test, List<BlockWrapper>  blocks){
        super.setId(test.getId());
        super.setLastAnswered(test.getLastAnswered());
        super.setLastModified(test.getLastModified());
        super.setManager(test.getManager());
        super.setName(test.getName());
        super.setNumberOfFiledCopies(test.getNumberOfFiledCopies());
        super.setNumberOfQuestions(test.getNumberOfQuestions());
        super.setNumberOfSubjects(test.getNumberOfSubjects());
        super.setState(test.getState());

        this.blocks = blocks;
    }

    public TestWrapper(CognitiveTest test) {
        super.setId(test.getId());
        super.setLastAnswered(test.getLastAnswered());
        super.setLastModified(test.getLastModified());
        super.setManager(test.getManager());
        super.setName(test.getName());
        super.setNumberOfFiledCopies(test.getNumberOfFiledCopies());
        super.setNumberOfQuestions(test.getNumberOfQuestions());
        super.setNumberOfSubjects(test.getNumberOfSubjects());
        super.setState(test.getState());

        this.blocks = new ArrayList<>();
    }


    public List<BlockWrapper> getBlocks(){
        return blocks;
    }
}
