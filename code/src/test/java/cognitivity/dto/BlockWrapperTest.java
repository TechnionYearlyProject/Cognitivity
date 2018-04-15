package cognitivity.dto;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


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
        assertTrue("Problem with block wrapper with empty list of questions", empty.getQuestions().isEmpty());

        BlockWrapper empty2 = new BlockWrapper(5 ,true, "tag", test);
        assertTrue("Problem with block wrapper with empty list of questions", empty.getQuestions().isEmpty());

        BlockWrapper wrapper = new BlockWrapper(questions, block);

        List<TestQuestion> res = wrapper.getQuestions();
        for (TestQuestion t : res) {
            assertTrue("Getting unrelated questions while trying to get all questions", questions.contains(t));
        }
        for (TestQuestion t : questions) {
            assertTrue("Didn't get all the questions", res.contains(t));
        }

        wrapper.setId(5L);
        assertTrue(wrapper.getId() == 5);

        wrapper.setNumberOfQuestions(10);
        assertTrue(wrapper.getNumberOfQuestions() == 10);

        wrapper.setRandomize(true);
        assertTrue(wrapper.getRandomize());

        wrapper.setTag("hello world");
        assertTrue(wrapper.getTag().equals("hello world"));

        TestManager testManager = new TestManager("a;lkjflsa");
        CognitiveTest cognitiveTest = new CognitiveTest("hey cognitive", testManager, 1, 10, "notes", "project");
        cognitiveTest.setId(15L);
        wrapper.setCognitiveTest(cognitiveTest);
        assertTrue(wrapper.getCognitiveTest().getId() == 15L);

        List<TestQuestion> emptyList = new ArrayList<>();
        wrapper.setQuestions(emptyList);
        assertTrue(wrapper.getQuestions() == emptyList);

        TestBlock testBlock = new TestBlock(10, false, "safdj", cognitiveTest);
        BlockWrapper constructorCheck = new BlockWrapper(emptyList, testBlock);
        assertTrue(constructorCheck.getTag().equals("safdj"));

        BlockWrapper lastConstructor = new BlockWrapper(emptyList,
                1500, false, "mug", cognitiveTest);
        assertTrue(lastConstructor.getNumberOfQuestions() == 1500);


    }

}