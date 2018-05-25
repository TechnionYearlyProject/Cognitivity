package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;
import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class QuestionCoverTest {

    public static Question createQuestion(QuestionType type, QuestionPosition position) {
        return new Question(
                "text",
                type,
                position,
                true,
                new String[]{"tag"},
                "link",
                true
        );
    }

    @Test
    public void testQuestionStructure() {
        Question question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.BottomLeft);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.BottomMiddle);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.BottomRight);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.MiddleLeft);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.MiddleMiddle);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.MiddleRight);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.UpperLeft);
        question = createQuestion(QuestionType.DrillDownQuestion, QuestionPosition.UpperMiddle);
        question = createQuestion(QuestionType.MultipleChoice, QuestionPosition.UpperRight);
        question = createQuestion(QuestionType.OpenQuestion, QuestionPosition.UpperRight);
        question = createQuestion(QuestionType.RateQuestion, QuestionPosition.UpperRight);
        question.setPictureLink(question.getPictureLink());
        question.setPosition(question.getPosition());
        question.setQuestionType(question.getQuestionType());
        question.setTags(question.getTags());
        question.setText(question.getText());
    }

}
