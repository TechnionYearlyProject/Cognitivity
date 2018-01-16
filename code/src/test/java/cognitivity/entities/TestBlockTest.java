package cognitivity.entities;

import org.junit.Test;

import static cognitivity.entities.CognitiveTestTest.createCognitiveTest;

/**
 * Created by ophir on 15/01/18.
 */
public class TestBlockTest {

    public static TestBlock createTestBlock() {
        return new TestBlock(1, true, "tag", createCognitiveTest());
    }

    @Test
    public void gettersSettersTest() {
        TestBlock testBlock = new TestBlock();
        testBlock = createTestBlock();
        testBlock.setNumberOfQuestions(testBlock.getNumberOfQuestions() + 1);
        testBlock.setRandomize(!testBlock.getRandomize());
        testBlock.setTag(testBlock.getTag() + "");
        testBlock.setCognitiveTest(testBlock.getCognitiveTest());
    }
}
