package cognitivity.dto;

import cognitivity.model.TestAnswer;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Data Transfer Object [JSON] representing a cognitive test subject's answer's information.
 *
 */


public class TestAnswerDTO {


    public static TestAnswerDTO mapFromTestAnswerEntity(TestAnswer answer) {
        return new TestAnswerDTO();
    }

    public static List<TestAnswerDTO> mapFromCognitiveTestEntities(List<TestAnswer> answers) {
        return answers.stream()
                .map(TestAnswerDTO::mapFromTestAnswerEntity)
                .collect(Collectors.toList());
    }
}
