package cognitivity.entities;

import org.junit.Test;

/**
 * Created by ophir on 15/01/18.
 */
public class TestSubjectTest {

    public static TestSubject createTestSubject() {
        return new TestSubject("name", 1, "firefox");
    }

    @Test
    public void gettersSettersTest() {
        TestSubject testSubject = createTestSubject();
        testSubject.setBrowser(testSubject.getBrowser());
        testSubject.setIpAddress(testSubject.getIpAddress());
        testSubject.setId(testSubject.getId());
    }
}
