package cognitivity.entities;

import org.junit.Test;

import static cognitivity.entities.CognitiveTestTest.createCognitiveTest;
import static cognitivity.entities.CognitiveTestTest.createTestManager;
import static cognitivity.entities.TestBlockTest.createTestBlock;

/**
 * Created by ophir on 15/01/18.
 */
public class TestQuestionTest {

    public static TestQuestion createTestQuestion() {
        return new TestQuestion("q", createTestBlock(), createCognitiveTest(), createTestManager());
    }

    @Test
    public void gettersSettersTest() {
        TestQuestion testQuestion = createTestQuestion();
        testQuestion.setTestBlock(testQuestion.getTestBlock());
        testQuestion.setCognitiveTest(testQuestion.getCognitiveTest());
        testQuestion.setQuestion(testQuestion.getQuestion());
        testQuestion.setTestManager(testQuestion.getTestManager());
    }
}
