package cognitivity.entities;

import org.junit.Test;

public class CognitiveTestTest {
    @Test
    public void gettersSettetrsTest(){
        TestManager testManager = new TestManager("onlyForTests","asafds");
        CognitiveTest cognitiveTest = new CognitiveTest("aslkdjsdf", testManager);
    }
}
