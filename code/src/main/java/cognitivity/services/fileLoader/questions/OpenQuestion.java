package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;

/**
 * Created by ophir on 12/05/18.
 */
public class OpenQuestion extends Question {
    public OpenQuestion(String text, QuestionType type, QuestionPosition position, boolean hasConfidenceBar,
                        String[] tags, String pictureLink, boolean hasTimeMeasurement, String answerText) {
        super(text, type, position, hasConfidenceBar, tags, pictureLink, hasTimeMeasurement);
        this.answerText = answerText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    private String answerText;
}
