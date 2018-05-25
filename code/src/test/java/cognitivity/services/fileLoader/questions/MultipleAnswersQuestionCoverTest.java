package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.MultipleAnswersStructure;
import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;
import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class MultipleAnswersQuestionCoverTest extends QuestionCoverTest {
    public static MultipleAnswersQuestion createMultipleQuestion() {
        return new MultipleAnswersQuestion(
                "text",
                QuestionType.DrillDownQuestion,
                QuestionPosition.BottomLeft,
                true,
                new String[]{"tag"},
                "link",
                true,
                new String[]{"a1", "a2"},
                1,
                MultipleAnswersStructure.Horizontal
        );
    }

    @Test
    public void testStructure() {
        MultipleAnswersQuestion question = createMultipleQuestion();
        question.setAnswers(question.getAnswers());
        question.setCorrectAnswer(question.getCorrectAnswer());
        question.setStructure(question.getStructure());
    }
}
