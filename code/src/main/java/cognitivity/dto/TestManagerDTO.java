package cognitivity.dto;

import cognitivity.dao.TestManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object [JSON] representing a cognitive test manager's information.
 */


public class TestManagerDTO {

    private String name;
    private long id;

    public TestManagerDTO(String name, long id) {
        this.name = name;
        this.id = id;
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


    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestManagerDTO that = (TestManagerDTO) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
