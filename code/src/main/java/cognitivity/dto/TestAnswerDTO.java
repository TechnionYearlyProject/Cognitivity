package cognitivity.dto;

import cognitivity.dao.TestAnswer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a cognitive test subject's answer's information.
 */


public class TestAnswerDTO {

    private TestSubjectDTO testSubject;
    private TestQuestionDTO question;
    private CognitiveTestDTO cognitiveTest;
    private int numberOfClick;
    private int finalAnswer;
    private int questionPlacement;
    private int answerPlacement;
    private String verbalAnswer;
    private boolean questionWithPicture;
    private String timeToAnswer;
    private boolean timeMeasured;
    private boolean timeShowed;
    private boolean testeeExit;

    public TestAnswerDTO(TestSubjectDTO testSubject, TestQuestionDTO question, CognitiveTestDTO cognitiveTest,
                         int numberOfClick, int finalAnswer, int questionPlacement, int answerPlacement,
                         String verbalAnswer, boolean questionWithPicture, String timeToAnswer, boolean timeMeasured,
                         boolean timeShowed, boolean testeeExit) {
        this.testSubject = testSubject;
        this.question = question;
        this.cognitiveTest = cognitiveTest;
        this.numberOfClick = numberOfClick;
        this.finalAnswer = finalAnswer;
        this.questionPlacement = questionPlacement;
        this.answerPlacement = answerPlacement;
        this.verbalAnswer = verbalAnswer;
        this.questionWithPicture = questionWithPicture;
        this.timeToAnswer = timeToAnswer;
        this.timeMeasured = timeMeasured;
        this.timeShowed = timeShowed;
        this.testeeExit = testeeExit;
    }


    public static TestAnswerDTO mapFromTestAnswerEntity(TestAnswer answer) {
        return new TestAnswerDTO(TestSubjectDTO.mapFromTestSubjectEntity(answer.getTestSubject()),
                TestQuestionDTO.mapFromTestQuestionEntity(answer.getQuestion()),
                CognitiveTestDTO.mapFromCognitiveTestEntity(answer.getCognitiveTest()),
                answer.getNumberOfClick(),
                answer.getFinalAnswer(),
                answer.getQuestionPlacement(),
                answer.getAnswerPlacement(),
                answer.getVerbalAnswer(),
                answer.getQuestionWithPicture(),
                answer.getTimeToAnswer(),
                answer.getTimeMeasured(),
                answer.getTimeShowed(),
                answer.getTesteeExit());
    }

    public static List<TestAnswerDTO> mapFromCognitiveTestEntities(List<TestAnswer> answers) {
        return answers.stream()
                .map(TestAnswerDTO::mapFromTestAnswerEntity)
                .collect(Collectors.toList());
    }

    public TestSubjectDTO getTestSubject() {
        return testSubject;
    }

    public TestQuestionDTO getQuestion() {
        return question;
    }

    public CognitiveTestDTO getCognitiveTest() {
        return cognitiveTest;
    }

    public int getNumberOfClick() {
        return numberOfClick;
    }

    public int getFinalAnswer() {
        return finalAnswer;
    }

    public int getQuestionPlacement() {
        return questionPlacement;
    }

    public int getAnswerPlacement() {
        return answerPlacement;
    }

    public String getVerbalAnswer() {
        return verbalAnswer;
    }

    public boolean isQuestionWithPicture() {
        return questionWithPicture;
    }

    public String getTimeToAnswer() {
        return timeToAnswer;
    }

    public boolean isTimeMeasured() {
        return timeMeasured;
    }

    public boolean isTimeShowed() {
        return timeShowed;
    }

    public boolean isTesteeExit() {
        return testeeExit;
    }
}
