package cognitivity.dto;

import cognitivity.dao.TestQuestion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object representing a cognitive test question's information.
 */


public class TestQuestionDTO extends AbstractDTO {

    private long id;

    private String question;
    private int questionType;
    private int answer;

    private TestQuestionDTO(long id, String questionText, int questionType, int answerIndex) {
        super(id);
        this.question = questionText;
        this.questionType = questionType;
        this.answer = answerIndex;
    }

    public static TestQuestionDTO mapFromTestQuestionEntity(TestQuestion question) {
        return new TestQuestionDTO(question.getId(), question.getQuestion(),
                question.getQuestionType(),
                question.getAnswer());
    }

    public static List<TestQuestionDTO> mapFromTestQuestionEntities(List<TestQuestion> questions) {
        return questions.stream()
                .map(TestQuestionDTO::mapFromTestQuestionEntity)
                .collect(Collectors.toList());
    }


    public String getQuestion() {
        return question;
    }

    public int getQuestionType() {
        return questionType;
    }

    public int getAnswer() {
        return answer;
    }
}
