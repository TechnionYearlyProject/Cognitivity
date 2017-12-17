package cognitivity.dto;

import cognitivity.dao.TestSubject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a test subject's information.
 */


public class TestSubjectDTO {

    private String name;
    private int ipAddress;  // should maybe be something else?
    private String browser;

    public TestSubjectDTO(String name, int ipAddress, String browser) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.browser = browser;
    }


    public static TestSubjectDTO mapFromTestSubjectEntity(TestSubject subject) {
        return new TestSubjectDTO(subject.getName(),
                subject.getIpAddress(),
                subject.getBrowser());
    }

    public static List<TestSubjectDTO> mapFromTestSubjectEntities(List<TestSubject> subjects) {
        return subjects.stream()
                .map(TestSubjectDTO::mapFromTestSubjectEntity)
                .collect(Collectors.toList());
    }
}
