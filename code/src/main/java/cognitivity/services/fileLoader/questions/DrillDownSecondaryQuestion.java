package cognitivity.services.fileLoader.questions;

/**
 * Created by ophir on 12/05/18.
 */
public class DrillDownSecondaryQuestion {
    private String text;
    private String[] answers;
    private int correctAnswer;
    private int indexOfMainQuestion;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    public int getIndexOfMainQuestion() {
        return indexOfMainQuestion;
    }

    public void setIndexOfMainQuestion(int indexOfMainQuestion) {
        this.indexOfMainQuestion = indexOfMainQuestion;
    }

    public DrillDownSecondaryQuestion(String text, String[] answers, int correctAnswer, int indexOfMainQuestion) {

        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.indexOfMainQuestion = indexOfMainQuestion;
    }
}
