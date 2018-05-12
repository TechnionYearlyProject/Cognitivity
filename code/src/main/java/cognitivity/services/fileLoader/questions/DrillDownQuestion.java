package cognitivity.services.fileLoader.questions;

import cognitivity.services.fileLoader.QuestionPosition;
import cognitivity.services.fileLoader.QuestionType;

/**
 * Created by ophir on 12/05/18.
 */
public class DrillDownQuestion extends Question {
    public DrillDownQuestion(String text, QuestionType type, QuestionPosition position, boolean hasConfidenceBar,
                             String[] tags, String pictureLink, boolean hasTimeMeasurement, String[] answersForMain,
                             int correctForMain, DrillDownSecondaryQuestion[] secondaryQuestions) {
        super(text, type, position, hasConfidenceBar, tags, pictureLink, hasTimeMeasurement);
        this.answersForMain = answersForMain;
        this.correctForMain = correctForMain;
        this.secondaryQuestions = secondaryQuestions;
    }

    private String[] answersForMain;
    private int correctForMain;
    private DrillDownSecondaryQuestion[] secondaryQuestions;
}
