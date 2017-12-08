package cognitivity.dto;

import cognitivity.model.CognitiveTest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a cognitive test's information.
 */
public class CognitiveTestDTO {

    private final int id;
    private final String name;
    private final TestManagerDTO manager;
    private final int numberOfSubjects;
    private final int state;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "UTC")
    private final Date lastModified;
    private final String lastAnswered;
    private final int numberOfFiledCopies;
    private final int numberOfQuestions;

    private CognitiveTestDTO(int id, String name, TestManagerDTO manager, int numberOfSubjects, int state,
                             Date lastModified, String lastAnswered, int numberOfFiledCopies, int numberOfQuestions) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.numberOfSubjects = numberOfSubjects;
        this.state = state;
        this.lastModified = lastModified;
        this.lastAnswered = lastAnswered;
        this.numberOfFiledCopies = numberOfFiledCopies;
        this.numberOfQuestions = numberOfQuestions;
    }


    public static CognitiveTestDTO mapFromCognitiveTestEntity(CognitiveTest test) {
        return new CognitiveTestDTO(
                test.getId(),
                test.getName(),
                TestManagerDTO.mapFromTestManagerEntity(test.getManager()),
                test.getNumberOfTestees(),
                test.getState(),
                test.getLastModified(),
                test.getLastAnswered(),
                test.getNumberOfFiledCopies(),
                test.getNumberOfQuestions());
    }

    public static List<CognitiveTestDTO> mapFromCognitiveTestEntities(List<CognitiveTest> tests) {
        return tests.stream()
                .map(CognitiveTestDTO::mapFromCognitiveTestEntity)
                .collect(Collectors.toList());
    }

}
