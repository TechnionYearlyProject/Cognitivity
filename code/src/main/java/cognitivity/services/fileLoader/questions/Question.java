package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;

/**
 * Created by ophir on 12/05/18.
 */
public class Question {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionPosition getPosition() {
        return position;
    }

    public void setPosition(QuestionPosition position) {
        this.position = position;
    }

    public boolean hasConfidenceBar() {
        return hasConfidenceBar;
    }

    public void hasConfidenceBar(boolean hasConfidenceBar) {
        this.hasConfidenceBar = hasConfidenceBar;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public boolean hasTimeMeasurement() {
        return hasTimeMeasurement;
    }

    public void hasTimeMeasurement(boolean hasTimeMeasurement) {
        this.hasTimeMeasurement = hasTimeMeasurement;
    }

    public Question(String text, QuestionType questionType, QuestionPosition position, boolean hasConfidenceBar, String[] tags,
                    String pictureLink, boolean hasTimeMeasurement) {
        this.text = text;
        this.questionType = questionType;
        this.position = position;
        this.hasConfidenceBar = hasConfidenceBar;
        this.tags = tags;
        this.pictureLink = pictureLink;
        this.hasTimeMeasurement = hasTimeMeasurement;
    }

    protected String text;
    protected QuestionType questionType;
    protected QuestionPosition position;
    protected boolean hasConfidenceBar;
    protected String[] tags;
    protected String pictureLink;
    protected boolean hasTimeMeasurement;
}
