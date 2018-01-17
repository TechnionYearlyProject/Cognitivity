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
        return new TestQuestion("q", 1, 1, "tag", createTestBlock(), createCognitiveTest(), createTestManager(), 0);
    }

    @Test
    public void gettersSettersTest() {
        TestQuestion testQuestion = createTestQuestion();
        testQuestion.setAnswer(testQuestion.getAnswer());
        testQuestion.setBlock(testQuestion.getBlock());
        testQuestion.setCognitiveTest(testQuestion.getCognitiveTest());
        testQuestion.setQuestion(testQuestion.getQuestion());
        testQuestion.setTag(testQuestion.getTag());
        testQuestion.setQuestionType(testQuestion.getQuestionType());
        testQuestion.setTestManager(testQuestion.getTestManager());
    }
}
