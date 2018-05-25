package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;
import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class RateQuestionCoverTest extends QuestionCoverTest {
    public static RateQuestion createRateQuestion() {
        return new RateQuestion(
                "text",
                QuestionType.DrillDownQuestion,
                QuestionPosition.BottomLeft,
                true,
                new String[]{"tag"},
                "link",
                true,
                5
        );
    }

    @Test
    public void testStructure() {
        RateQuestion question = createRateQuestion();
        question.setMaxRating(question.getMaxRating());
    }
}
