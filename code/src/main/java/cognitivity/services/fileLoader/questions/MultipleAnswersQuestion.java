package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.MultipleAnswersStructure;
import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;

/**
 * Created by ophir on 12/05/18.
 */
public class MultipleAnswersQuestion extends Question {
    public MultipleAnswersQuestion(String text, QuestionType type, QuestionPosition position, boolean hasConfidenceBar,
                                   String[] tags, String pictureLink, boolean hasTimeMeasurement, String[] answers,
                                   int correctAnswer, MultipleAnswersStructure structure) {
        super(text, type, position, hasConfidenceBar, tags, pictureLink, hasTimeMeasurement);
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.structure = structure;
    }

    private String[] answers;
    private int correctAnswer;

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public MultipleAnswersStructure getStructure() {
        return structure;
    }

    public void setStructure(MultipleAnswersStructure structure) {
        this.structure = structure;
    }

    private MultipleAnswersStructure structure;
}
