package cognitivity.entities;

import org.junit.Test;

import static cognitivity.entities.CognitiveTestTest.createCognitiveTest;
import static cognitivity.entities.TestQuestionTest.createTestQuestion;
import static cognitivity.entities.TestSubjectTest.createTestSubject;

/**
 * Created by ophir on 15/01/18.
 */
public class TestAnswerTest {

    @Test
    public void gettersSettersTest() {
        TestAnswer testAnswer = new TestAnswer(createTestSubject(), createTestQuestion(), createCognitiveTest(),
                "v");

        testAnswer.setCognitiveTest(testAnswer.getCognitiveTest());
        testAnswer.setFinalAnswer(testAnswer.getFinalAnswer());
        testAnswer.setQuestion(testAnswer.getQuestion());
        testAnswer.setTestSubject(testAnswer.getTestSubject());

        // For abstract entity
        testAnswer.equals(null);
        testAnswer.equals(1);
        testAnswer.equals(testAnswer);
        testAnswer.hashCode();
        testAnswer.setId(1L);
        testAnswer.hashCode();
        testAnswer.equals(testAnswer);
    }
}
