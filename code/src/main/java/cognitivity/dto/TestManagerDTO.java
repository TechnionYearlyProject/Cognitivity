package cognitivity.dto;

import cognitivity.dao.TestManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Data Transfer Object [JSON] representing a cognitive test manager's information.
 *
 */


public class TestManagerDTO {

    public static TestManagerDTO mapFromTestManagerEntity(TestManager answer) {
        return new TestManagerDTO();
    }

    public static List<TestManagerDTO> mapFromTestManagerEntities(List<TestManager> managers) {
        return managers.stream()
                .map(TestManagerDTO::mapFromTestManagerEntity)
                .collect(Collectors.toList());
    }
}
