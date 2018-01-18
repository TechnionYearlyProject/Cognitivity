package cognitivity.dto;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TestWrapperTest {

    @Test
    public void getBlocks(){
        TestManager manager = new TestManager("Big Boss");
        TestBlock block = new TestBlock();
        TestBlock block2 = new TestBlock();
        TestBlock block3 = new TestBlock();

        BlockWrapper wrapper = new BlockWrapper(block);
        BlockWrapper wrapper1 = new BlockWrapper(block2);
        BlockWrapper wrapper2 = new BlockWrapper(block3);

        CognitiveTest test = new CognitiveTest();

        List<BlockWrapper> wrappers = new ArrayList<BlockWrapper>();
        wrappers.add(wrapper);
        wrappers.add(wrapper1);
        wrappers.add(wrapper2);

        TestWrapper testWrapper = new TestWrapper(test, wrappers);
        TestWrapper empty = new TestWrapper(test);

        assertEquals("Doesn't create empty list for constructor without a list",null, empty.getBlocks());

        List<BlockWrapper> res = testWrapper.getBlocks();
        for (BlockWrapper t : res) {
            assertTrue("Getting unrelated results while trying to get all Blocks", wrappers.contains(t));
        }
        for (BlockWrapper t : wrappers) {
            assertTrue("Didn't get all the Blocks", res.contains(t));
        }



    }

}