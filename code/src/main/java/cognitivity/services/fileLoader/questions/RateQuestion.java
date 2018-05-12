package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;

/**
 * Created by ophir on 12/05/18.
 */
public class RateQuestion extends Question {
    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }

    public RateQuestion(String text, QuestionType type, QuestionPosition position, boolean hasConfidenceBar,
                        String[] tags, String pictureLink, boolean hasTimeMeasurement, int maxRating) {
        super(text, type, position, hasConfidenceBar, tags, pictureLink, hasTimeMeasurement);
        this.maxRating = maxRating;

    }

    private int maxRating;
}
