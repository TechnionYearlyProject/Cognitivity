package cognitivity.dto;

import cognitivity.dao.CognitiveTest;
import cognitivity.dao.TestAnswer;
import cognitivity.dao.TestQuestion;
import cognitivity.dao.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ophir on 17/12/17.
 */
public class TestAnswerDTOTest implements DTOTest {

    private List<TestAnswer> answers = new ArrayList<>();

    @Before
    public void setup() {
        TestAnswer answer1 = new TestAnswer();
        answer1.setId(1L);
        answer1.setAnswerPlacement(1);
        answer1.setFinalAnswer(1);
        answer1.setNumberOfClick(1);
        answer1.setQuestionPlacement(1);
        answer1.setQuestionWithPicture(true);
        answer1.setTesteeExit(true);
        answer1.setTimeMeasured(true);
        answer1.setTimeShowed(true);
        answer1.setTimeToAnswer("1");
        answer1.setVerbalAnswer("1");
        TestQuestion q1 = new TestQuestion();
        q1.setId(1L);
        answer1.setQuestion(q1);
        TestSubject s1 = new TestSubject();
        s1.setId(1L);
        answer1.setTestSubject(s1);
        CognitiveTest t1 = new CognitiveTest();
        t1.setId(1L);
        answer1.setCognitiveTest(t1);


        TestAnswer answer2 = new TestAnswer();
        answer2.setId(2L);
        answer2.setAnswerPlacement(2);
        answer2.setFinalAnswer(2);
        answer2.setNumberOfClick(2);
        answer2.setQuestionPlacement(2);
        answer2.setQuestionWithPicture(false);
        answer2.setTesteeExit(false);
        answer2.setTimeMeasured(false);
        answer2.setTimeShowed(false);
        answer2.setTimeToAnswer("2");
        answer2.setVerbalAnswer("2");
        TestQuestion q2 = new TestQuestion();
        q2.setId(2L);
        answer2.setQuestion(q2);
        TestSubject s2 = new TestSubject();
        s2.setId(2L);
        answer2.setTestSubject(s2);
        CognitiveTest t2 = new CognitiveTest();
        t2.setId(2L);
        answer2.setCognitiveTest(t2);

        answers.add(answer1);
        answers.add(answer2);
    }


    @Override
    @Test
    public void checkMapEntity() {
        TestAnswer answer = answers.get(0);
        TestAnswerDTO answerDTO = TestAnswerDTO.mapFromTestAnswerEntity(answer);
        Assert.assertTrue(answer.getId().equals(answerDTO.getId()));
        Assert.assertTrue(answer.getAnswerPlacement().equals(answerDTO.getAnswerPlacement()));
        Assert.assertTrue(answer.getFinalAnswer().equals(answerDTO.getFinalAnswer()));
        Assert.assertTrue(answer.getNumberOfClick().equals(answerDTO.getNumberOfClick()));
        Assert.assertEquals(answer.getQuestionWithPicture(), answerDTO.isQuestionWithPicture());
        Assert.assertEquals(answer.getTesteeExit(), answerDTO.isTesteeExit());
        Assert.assertEquals(answer.getTimeMeasured(), answerDTO.isTimeMeasured());
        Assert.assertEquals(answer.getTimeShowed(), answerDTO.isTimeShowed());
        Assert.assertEquals(answer.getTimeToAnswer(), answerDTO.getTimeToAnswer());
        Assert.assertEquals(answer.getVerbalAnswer(), answerDTO.getVerbalAnswer());
        Assert.assertEquals(TestSubjectDTO.mapFromTestSubjectEntity(answer.getTestSubject()), answerDTO.getTestSubject());
        Assert.assertEquals(CognitiveTestDTO.mapFromCognitiveTestEntity(answer.getCognitiveTest()), answerDTO.getCognitiveTest());
    }

    @Override
    @Test
    public void checkMapListEntities() {
        List<TestAnswerDTO> answerDTOS = TestAnswerDTO.mapFromCognitiveTestEntities(answers);
        for (int i = 0; i < answers.size(); i++) {
            TestAnswer answer = answers.get(0);
            TestAnswerDTO answerDTO = answerDTOS.get(0);
            Assert.assertTrue(answer.getId().equals(answerDTO.getId()));
            Assert.assertTrue(answer.getAnswerPlacement().equals(answerDTO.getAnswerPlacement()));
            Assert.assertTrue(answer.getFinalAnswer().equals(answerDTO.getFinalAnswer()));
            Assert.assertTrue(answer.getNumberOfClick().equals(answerDTO.getNumberOfClick()));
            Assert.assertEquals(answer.getQuestionWithPicture(), answerDTO.isQuestionWithPicture());
            Assert.assertEquals(answer.getTesteeExit(), answerDTO.isTesteeExit());
            Assert.assertEquals(answer.getTimeMeasured(), answerDTO.isTimeMeasured());
            Assert.assertEquals(answer.getTimeShowed(), answerDTO.isTimeShowed());
            Assert.assertEquals(answer.getTimeToAnswer(), answerDTO.getTimeToAnswer());
            Assert.assertEquals(answer.getVerbalAnswer(), answerDTO.getVerbalAnswer());
            Assert.assertEquals(TestSubjectDTO.mapFromTestSubjectEntity(answer.getTestSubject()), answerDTO.getTestSubject());
            Assert.assertEquals(CognitiveTestDTO.mapFromCognitiveTestEntity(answer.getCognitiveTest()), answerDTO.getCognitiveTest());
        }
    }
}
