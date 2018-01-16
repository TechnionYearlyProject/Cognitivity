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
                1, 1, 1, 1, "v", true,
                1, true, true, true);

        testAnswer.setAnswerPlacement(testAnswer.getAnswerPlacement());
        testAnswer.setCognitiveTest(testAnswer.getCognitiveTest());
        testAnswer.setFinalAnswer(testAnswer.getFinalAnswer());
        testAnswer.setNumberOfClick(testAnswer.getNumberOfClick());
        testAnswer.setQuestion(testAnswer.getQuestion());
        testAnswer.setQuestionPlacement(testAnswer.getQuestionPlacement());
        testAnswer.setQuestionWithPicture(testAnswer.getQuestionWithPicture());
        testAnswer.setTesteeExit(testAnswer.getTesteeExit());
        testAnswer.setTestSubject(testAnswer.getTestSubject());
        testAnswer.setTimeShowed(testAnswer.getTimeShowed());
        testAnswer.setTimeMeasured(testAnswer.getTimeMeasured());
        testAnswer.setTimeToAnswer(testAnswer.getTimeToAnswer());
        testAnswer.setVerbalAnswer(testAnswer.getVerbalAnswer());

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
