package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;
import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class OpenQuestionCoverTest extends QuestionCoverTest {
    public static OpenQuestion createOpenQuestion() {
        return new OpenQuestion(
                "text",
                QuestionType.DrillDownQuestion,
                QuestionPosition.BottomLeft,
                true,
                new String[]{"tag"},
                "link",
                true,
                "answer"
        );
    }

    @Test
    public void testStructure() {
        OpenQuestion question = createOpenQuestion();
        question.setAnswerText(question.getAnswerText());
    }
}
