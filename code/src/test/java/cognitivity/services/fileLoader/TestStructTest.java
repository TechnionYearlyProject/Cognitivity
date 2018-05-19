package cognitivity.services.fileLoader;

/**
 * Created by ophir on 14/05/18.
 */
public class TestStructTest {

    public static Test create() {
        return new Test(TestManagerStructureTest.create(), "test1", "a note", "project1", null);
    }

    @org.junit.Test
    public void testStructure() {

        Test test = create();
    }
}
