package cognitivity.entities;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestManagerTest {

    @Test
    public void gettersSettersTest() {

        TestManager manager = new TestManager();
        assertEquals("Problem with creating empty manager",null, manager.getEmail());
        manager.setEmail("BlaBla@bla.com");
        assertEquals("Problem with setting email", "BlaBla@bla.com", manager.getEmail());

        TestManager manager1 = new TestManager("RealEmail@FakeEmail.com");

        assertEquals("Problem with creating a test manager with given Email", manager1.getEmail(), "RealEmail@FakeEmail.com");
    }
}
