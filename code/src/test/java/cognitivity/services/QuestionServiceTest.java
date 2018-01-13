package java.cognitivity.services;

import cognitivity.entities.*;
import cognitivity.services.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionServiceTest {

    /**
     * Testing all functionalities Question service
     */
    @Test
    public void fullTest(){
        QuestionService service = new QuestionService();
        TestBlockService blockService = new TestBlockService();
        CognitiveTestService testService = new CognitiveTestService();
        TestManagerService managerService = new TestManagerService();

        TestManager manager = managerService.createTestManager("The bossy boss boss","Admin Admin");
        CognitiveTest test = testService.createTestForTestManager("teee",manager, 1, 100);
        TestBlock block = blockService.createTestBlock(5,true,"Taggy tag",test);


        TestQuestion question = service.createTestQuestion("What is the meaning of life?", 1, 42, "Unanswered questions",block, test,manager);

        assertNotNull("Problem with creating a test question", question);

        TestQuestion result = service.findQuestionById(question.getId());

        assertEquals("Problem with getting a test question by ID", question,result);

        question.setAnswer(43);

        service.updateTestQuestion(question);

        result = service.findQuestionById(question.getId());
        int numericAnswer  = result.getAnswer();

        assertEquals("Problem with updating the question", 43, numericAnswer);


        TestBlock block2 = blockService.createTestBlock(2,true,"Togos",test);
        TestQuestion question1 = service.createTestQuestion("Who moved my cheese?", 5, 52, "Critical for life",block, test,manager);
        TestQuestion question2 = service.createTestQuestion("Who framed Roger Rabbit?", 1, 54, "Movie questions",block2, test,manager);
        TestQuestion question3 = service.createTestQuestion("Question! Question?", 1, 42, "What?! Who?!",block2, test,manager);

        List<TestQuestion> questions = new ArrayList<>();
        questions.add(question);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        List<TestQuestion> questions1 = service.findAllTestQuestionsFromTestId(test.getId());
        for (TestQuestion t : questions1){
            assertTrue("Getting unrelated results while trying to get all test questions",questions.contains(t));
        }
        for (TestQuestion t : questions){
            assertTrue("Didn't get all the questions from the test",questions1.contains(t));
        }

        CognitiveTest test2 = testService.createTestForTestManager("Cognitivity2. Coming to the nearest theater",manager, 1, 100);

        TestQuestion question4 = service.createTestQuestion("Who moved my cheese?", 5, 52, "Critical for life",block, test2,manager);
        TestQuestion question5 = service.createTestQuestion("Who framed Roger Rabbit?", 1, 54, "Movie questions",block2, test2,manager);
        TestQuestion question6 = service.createTestQuestion("Question! Question?", 1, 42, "What?! Who?!",block2, test2,manager);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);

        questions1 = service.findAllTestQuestionsFromManagerId(manager.getId());
        for (TestQuestion t : questions1){
            assertTrue("Getting unrelated results while trying to get all test questions from all tests",questions.contains(t));
        }
        for (TestQuestion t : questions){
            assertTrue("Didn't get all the questions from all the tests",questions1.contains(t));
        }

        TestAnswerService answerService = new TestAnswerService();
        TestSubjectService subjectService = new TestSubjectService();
        TestSubject subject = subjectService.createTestSubject("Timothy k miller",111,"Safchrome");
        TestAnswer answer = answerService.addTestAnswerForTestQuestion(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        TestAnswer answer1 = answerService.addTestAnswerForTestQuestion(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        TestAnswer answer2 = answerService.addTestAnswerForTestQuestion(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);
        TestAnswer answer3 = answerService.addTestAnswerForTestQuestion(subject,question,test,52,43,2,3,"Bla is bla",true,52,
                false,false,true);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        List<TestAnswer> res = service.getTestAnswers(question.getId());
        for (TestAnswer t : res){
            assertTrue("Getting unrelated results while trying to get all the answers to the question",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the question answers",res.contains(t));
        }

        service.deleteTestQuestion(question.getId());
        service.deleteTestQuestion(question1.getId());
        service.deleteTestQuestion(question2.getId());
        service.deleteTestQuestion(question3.getId());
        service.deleteTestQuestion(question4.getId());
        service.deleteTestQuestion(question5.getId());
        service.deleteTestQuestion(question6.getId());

        managerService.deleteTestManager(manager.getId());;

        blockService.deleteTestBlock(block.getId());

        testService.deleteTestForTestManager(test.getId());

    }




}