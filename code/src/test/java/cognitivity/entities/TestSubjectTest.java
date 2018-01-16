package cognitivity.entities;

import org.junit.Test;

/**
 * Created by ophir on 15/01/18.
 */
public class TestSubjectTest {

    @Test
    public void gettersSettersTest() {
        TestSubject testSubject = new TestSubject("name", 1, "firefox");
        testSubject.setBrowser(testSubject.getBrowser());
        testSubject.setIpAddress(testSubject.getIpAddress());
        testSubject.setId(testSubject.getId());
    }
}
