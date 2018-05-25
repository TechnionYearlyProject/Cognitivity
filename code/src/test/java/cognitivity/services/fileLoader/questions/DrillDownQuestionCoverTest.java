package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;
import org.junit.Test;

import static cognitivity.services.fileLoader.questions.DrillDownSecondaryQuestionCoverTest.createSecondary;

/**
 * Created by ophir on 25/05/18.
 */
public class DrillDownQuestionCoverTest extends QuestionCoverTest {
    public static DrillDownQuestion createDrillDown() {
        return new DrillDownQuestion(
                "text",
                QuestionType.DrillDownQuestion,
                QuestionPosition.BottomLeft,
                true,
                new String[]{"tag"},
                "link",
                true,
                new String[]{"a1", "a2"},
                1,
                new DrillDownSecondaryQuestion[]{
                        createSecondary()
                }
        );
    }

    @Test
    public void testStructure() {
        DrillDownQuestion question = createDrillDown();
        question.setAnswersForMain(question.getAnswersForMain());
        question.setCorrectForMain(question.getCorrectForMain());
        question.setSecondaryQuestions(question.getSecondaryQuestions());
    }
}
