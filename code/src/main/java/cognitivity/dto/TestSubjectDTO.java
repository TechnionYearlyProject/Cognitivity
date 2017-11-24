package cognitivity.dto;

import cognitivity.model.TestSubject;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Data Transfer Object [JSON] representing a test subject's information.
 *
 */


public class TestSubjectDTO {

    public static TestSubjectDTO mapFromTestSubjectEntity(TestSubject subject) {
        return new TestSubjectDTO();
    }

    public static List<TestSubjectDTO> mapFromTestSubjectEntities(List<TestSubject> subjects) {
        return subjects.stream()
                .map(TestSubjectDTO::mapFromTestSubjectEntity)
                .collect(Collectors.toList());
    }
}
