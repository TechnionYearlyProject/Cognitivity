package cognitivity.dto;

import cognitivity.dao.TestSubject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a test subject's information.
 */


public class TestSubjectDTO extends AbstractDTO {

    private String name;
    private int ipAddress;  // should maybe be something else?
    private String browser;

    public TestSubjectDTO(long id, String name, int ipAddress, String browser) {
        super(id);
        this.name = name;
        this.ipAddress = ipAddress;
        this.browser = browser;
    }


    public static TestSubjectDTO mapFromTestSubjectEntity(TestSubject subject) {
        return new TestSubjectDTO(subject.getId(), subject.getName(),
                subject.getIpAddress(),
                subject.getBrowser());
    }

    public static List<TestSubjectDTO> mapFromTestSubjectEntities(List<TestSubject> subjects) {
        return subjects.stream()
                .map(TestSubjectDTO::mapFromTestSubjectEntity)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public int getIpAddress() {
        return ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

}
