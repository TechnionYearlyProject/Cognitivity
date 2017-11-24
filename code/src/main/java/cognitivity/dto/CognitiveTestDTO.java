package cognitivity.dto;

import cognitivity.model.CognitiveTest;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Data Transfer Object [JSON] representing a cognitive test's information.
 *
 */
public class CognitiveTestDTO {

    public CognitiveTestDTO() {

    }

    public static CognitiveTestDTO mapFromCognitiveTestEntity(CognitiveTest test) {
        return new CognitiveTestDTO();
    }

    public static List<CognitiveTestDTO> mapFromCognitiveTestEntities(List<CognitiveTest> tests) {
        return tests.stream()
                .map(CognitiveTestDTO::mapFromCognitiveTestEntity)
                .collect(Collectors.toList());
    }

}
