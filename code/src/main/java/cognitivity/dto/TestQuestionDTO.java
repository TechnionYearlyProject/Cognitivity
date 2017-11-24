package cognitivity.dto;

import cognitivity.model.TestQuestion;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Data Transfer Object representing a cognitive test question's information.
 *
 */


public class TestQuestionDTO {


    public static TestQuestionDTO mapFromTestQuestionEntity(TestQuestion question) {
        return new TestQuestionDTO();
    }

    public static List<TestQuestionDTO> mapFromTestQuestionEntities(List<TestQuestion> questions) {
        return questions.stream()
                .map(TestQuestionDTO::mapFromTestQuestionEntity)
                .collect(Collectors.toList());
    }
}
