package cognitivity.entities;


import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * A wrapper class For cognitive test.
 * This class includes the wrapper blocks of the blocks in the test.
 */
public class TestWrapper extends CognitiveTest {
    private List<BlockWrapper>  blocks;


    @Autowired
    public TestWrapper(CognitiveTest test, CognitiveTestDAO dao, TestBlockDAO blockDAO){
        super.setId(test.getId());
        super.setLastAnswered(test.getLastAnswered());
        super.setLastModified(test.getLastModified());
        super.setManager(test.getManager());
        super.setName(test.getName());
        super.setNumberOfFiledCopies(test.getNumberOfFiledCopies());
        super.setNumberOfQuestions(test.getNumberOfQuestions());
        super.setNumberOfSubjects(test.getNumberOfSubjects());
        super.setState(test.getState());

        List<TestBlock> preWrapped = dao.getTestBlocks(this.getId());
        for (TestBlock block : preWrapped) {
            blocks.add(new BlockWrapper(blockDAO,block));
        }
    }

    public List<BlockWrapper> getBlocks(){
        return blocks;
    }
}
