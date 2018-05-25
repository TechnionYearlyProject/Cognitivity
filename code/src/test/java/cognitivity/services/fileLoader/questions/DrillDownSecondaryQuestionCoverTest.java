package cognitivity.services.fileLoader.questions;

import org.junit.Test;

/**
 * Created by ophir on 25/05/18.
 */
public class DrillDownSecondaryQuestionCoverTest {
    public static DrillDownSecondaryQuestion createSecondary() {
        return new DrillDownSecondaryQuestion(
                "text",
                new String[]{"1"},
                0,
                0
        );
    }

    @Test
    public void testStructure() {
        DrillDownSecondaryQuestion sec = createSecondary();
        sec.setAnswers(sec.getAnswers());
        sec.setCorrectAnswer(sec.getCorrectAnswer());
        sec.setIndexOfMainQuestion(sec.getIndexOfMainQuestion());
        sec.setText(sec.getText());
    }
}
