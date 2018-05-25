package cognitivity.services.fileLoader;

/**
 * Created by ophir on 14/05/18.
 */
public class TestStructCoverTest {

    public static Test create() {
        return new Test(TestManagerCoverTest.create(), "test1", "a note", "project1", null);
    }

    @org.junit.Test
    public void testStructure() {
        Test test = create();
        test.setBlocks(test.getBlocks());
        test.setName(test.getName());
        test.setNotes(test.getNotes());
        test.setProject(test.getProject());
        test.setTestManager(test.getTestManager());
    }
}
