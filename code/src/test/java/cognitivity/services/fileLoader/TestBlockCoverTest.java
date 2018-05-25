package cognitivity.services.fileLoader;

import cognitivity.services.fileLoader.questions.Question;
import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class TestBlockCoverTest {
    public static TestBlock createBlock() {
        return new TestBlock(
                1,
                "tag",
                true,
                new Question[]{}
        );
    }

    @Test
    public void testStructure() {
        TestBlock b = createBlock();
        b.setNumberOfQuestions(b.getNumberOfQuestions());
        b.setQuestions(b.getQuestions());
        b.setShuffle(b.isShuffle());
        b.setTag(b.getTag());
    }
}
