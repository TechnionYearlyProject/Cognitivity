package cognitivity.dto;

import cognitivity.dao.TestManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a cognitive test manager's information.
 */


public class TestManagerDTO extends AbstractDTO {

    private String name;

    public TestManagerDTO(String name, long id) {
        super(id);
        this.name = name;
    }

    public static TestManagerDTO mapFromTestManagerEntity(TestManager testManager) {
        return new TestManagerDTO(testManager.getName(),
                testManager.getId());
    }

    public static List<TestManagerDTO> mapFromTestManagerEntities(List<TestManager> managers) {
        return managers.stream()
                .map(TestManagerDTO::mapFromTestManagerEntity)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }


}
