package cognitivity.dto;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BlockWrapperTest {

    @Test
    public void getQuestions() throws Exception {
        TestBlock block = new TestBlock();

        CognitiveTest test = new CognitiveTest();

        TestQuestion question = new TestQuestion();
        TestQuestion question1 = new TestQuestion();
        TestQuestion question2 = new TestQuestion();

        List<TestQuestion> questions = new ArrayList<TestQuestion>();
        questions.add(question);
        questions.add(question1);
        questions.add(question2);

        BlockWrapper empty = new BlockWrapper(block);
        // assertEquals("Problem with block wrapper with empty list of questions", null, empty.getQuestions());

        BlockWrapper empty2 = new BlockWrapper(5 ,true, "tag", test);
        // assertEquals("Problem with block wrapper without list of questions", null, empty2.getQuestions());

        BlockWrapper wrapper = new BlockWrapper(questions, block);

        List<TestQuestion> res = wrapper.getQuestions();
        for (TestQuestion t : res) {
            assertTrue("Getting unrelated questions while trying to get all questions", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions", res.contains(t));
        }



    }

}