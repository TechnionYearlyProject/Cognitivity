package cognitivity.services.fileLoader;

/**
 * Created by ophir on 14/05/18.
 */
public class TestManagerCoverTest {

    public static final String EMAIL = "email@mail.com";

    public static TestManager create() {
        return new TestManager(EMAIL);
    }

    @org.junit.Test
    public void testStructure() {
        TestManager manager = create();
        manager.setEmail(manager.getEmail());
    }
}
