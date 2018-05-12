package cognitivity.services.fileLoader;

import cognitivity.services.fileLoader.questions.Question;

/**
 * Created by ophir on 12/05/18.
 */
public class TestBlock {
    private int numberOfQuestions;
    private String tag;
    private boolean shuffle;
    private Question[] questions;

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public TestBlock(int numberOfQuestions, String tag, boolean shuffle, Question[] questions) {

        this.numberOfQuestions = numberOfQuestions;
        this.tag = tag;
        this.shuffle = shuffle;
        this.questions = questions;
    }
}
