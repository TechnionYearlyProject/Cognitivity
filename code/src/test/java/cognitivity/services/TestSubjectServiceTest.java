package cognitivity.services;

import cognitivity.entities.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSubjectServiceTest {
    @Test
    public void FullTest(){
        TestSubjectService service = new TestSubjectService();
        CognitiveTestService testService = new CognitiveTestService();
        TestManagerService managerService = new TestManagerService();
        TestAnswerService answerService = new TestAnswerService();
        QuestionService questionService = new QuestionService();
        TestBlockService blockService = new TestBlockService();


        TestSubject subject = service.createTestSubject("Simha Gora",45645,"Queue");
        assertNotNull("Problem with creating a test subject",subject);

        TestSubject result = service.findTestSubject(subject.getId());

        assertEquals("Problem with getting a test subject",result,subject);

        subject.setBrowser("Tor");
        service.updateTestSubject(subject);

        result = service.findTestSubject(subject.getId());

        assertEquals("Problem with updating a test subject", "Tor",result.getBrowser());

        TestManager manager = managerService.createTestManager("Ein li Rayon Leod Shem","Maeshu kal");
        CognitiveTest test = testService.createTestForTestManager("test",manager, 1, 0);
        TestBlock block = blockService.createTestBlock(2,false,"teag", test);
        TestQuestion question = new TestQuestion("When will the Shibutzim arrive?",1,99999,"Questions that remain unasnwered",
                block, test, manager);
        questionService.createTestQuestion(question);

        TestSubject subject1 = service.createTestSubject("Timon",789,"Hakuna");
        TestSubject subject2 = service.createTestSubject("Pumba",654,"Matata");
        TestSubject subject3 = service.createTestSubject("Simba",12,"Safari");

        TestAnswer answer = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer);
        TestAnswer answer1 = new TestAnswer(subject1,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject2,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject3,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer3);

        List<TestSubject> subjects = new ArrayList<TestSubject>();
        subjects.add(subject);
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);

        List<TestSubject> testSubjectList = service.findTestSubjectsWhoParticipatedInTest(test.getId());
        for (TestSubject t : testSubjectList){
            assertTrue("Getting unrelated tests while trying to get all subjects from a specific test",subjects.contains(t));
        }
        for (TestSubject t : subjects){
            assertTrue("Didn't get all the subjects from a specific test",testSubjectList.contains(t));
        }

        TestAnswer answer4 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject,question, test, 5 , 5,
                4, 2, "To have no worries", false, 5, false,
                false, false);
        answerService.addTestAnswerForTestQuestion(answer6);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);

        List<TestAnswer> answerList = service.findAllTestSubjectAnswers(question.getId());
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answer while trying to get all answers of a specific subject",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers from a specific subject",answerList.contains(t));
        }


        service.deleteTestSubject(subject.getId());
        service.deleteTestSubject(subject1.getId());
        service.deleteTestSubject(subject2.getId());
        service.deleteTestSubject(subject3.getId());


        managerService.deleteTestManager(manager.getId());

        testService.deleteTestForTestManager(test.getId());

        blockService.deleteTestBlock(block.getId());

        questionService.deleteTestQuestion(question.getId());

        answerService.deleteAllTestAnswersForQuestion(answer.getId());
        answerService.deleteAllTestAnswersForQuestion(answer1.getId());
        answerService.deleteAllTestAnswersForQuestion(answer2.getId());
        answerService.deleteAllTestAnswersForQuestion(answer3.getId());
        answerService.deleteAllTestAnswersForQuestion(answer4.getId());
        answerService.deleteAllTestAnswersForQuestion(answer5.getId());
        answerService.deleteAllTestAnswersForQuestion(answer6.getId());


    }


}