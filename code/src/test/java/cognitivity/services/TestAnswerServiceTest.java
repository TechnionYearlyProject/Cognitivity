package cognitivity.services;

import cognitivity.entities.*;
import cognitivity.services.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;




public class TestAnswerServiceTest {

    /**
     * Testing all functionalities of TestAnswerService
     */
    @Test
    public void FullTest(){
        QuestionService questionService = new QuestionService();
        TestBlockService blockService = new TestBlockService();
        CognitiveTestService testService = new CognitiveTestService();
        TestManagerService managerService = new TestManagerService();
        TestSubjectService subjectService = new TestSubjectService();
        TestAnswerService service = new TestAnswerService();

        TestManager manager = managerService.createTestManager("Bohen Ben Mivhan","Paswordd");
        CognitiveTest test = testService.createTestForTestManager("Sifratiyot",manager,2,1);
        CognitiveTest test2 = testService.createTestForTestManager("jhfkasjhfkajdfak",manager,2,1);
        TestBlock block = blockService.createTestBlock(1,false,"tagiity tag",test);
        TestSubject subject = subjectService.createTestSubject("Rick",12321,"Ahla dafdefan");
        TestQuestion question = new TestQuestion("Who the f&$# builds a stonehenge?",4,5,
                "Questions we will never answer",block,test,manager);
        questionService.createTestQuestion(question);
        TestQuestion question1 = new TestQuestion("Who ate my sandwich?",4,5,
                "Questions we will never answer",block,test2,manager);
        questionService.createTestQuestion(question1);

        TestAnswer answer = new TestAnswer(subject,question,test,2,1,1,2,
                "None!",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer);

        assertNotNull("Problem with creating a test answer", answer);

        TestAnswer result = service.findTestAnswerById(answer.getId());

        assertEquals("Problem with getting an answer by Id",result,answer);

        answer.setNumberOfClick(5);
        service.updateTestAnswerForQuestion(answer);

        result = service.findTestAnswerById(answer.getId());
        int numericResult = result.getNumberOfClick();
        assertEquals("Problem with updating a question", 5, numericResult);


        TestAnswer answer1 = new TestAnswer(subject,question,test,2,1,1,2,
                "Yodel",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer1);
        TestAnswer answer2 = new TestAnswer(subject,question,test,2,1,1,2,
                "Operah",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer2);
        TestAnswer answer3 = new TestAnswer(subject,question,test,2,1,1,2,
                "Windows",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer3);

        List<TestAnswer> answers = new ArrayList<TestAnswer>();
        answers.add(answer);
        answers.add(answer3);
        answers.add(answer2);
        answers.add(answer1);

        List<TestAnswer> answerList = service.findAllTestAnswerForAQuestion(question.getId());
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answers while trying to get all answers for a specific question",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers for a specific question",answerList.contains(t));
        }

        TestAnswer answer4 = new TestAnswer(subject,question1,test2,2,1,1,2,
                "Snape killed Dumbeldore",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer4);
        TestAnswer answer5 = new TestAnswer(subject,question1,test2,2,1,1,2,
                "Bruce willis is dead on the 6th sense",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer5);
        TestAnswer answer6 = new TestAnswer(subject,question1,test2,2,1,1,2,
                "Brad Pit and Edward Norton are the same guy on Fight club",false,43,false,false, true);
        service.addTestAnswerForTestQuestion(answer6);


        answerList = service.findTestAnswersBySubjectInTest(subject.getId(),test.getId());
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answers while trying to get all answers for a specific test",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers for a specific test",answerList.contains(t));
        }
        answers.add(answer4);
        answers.add(answer5);
        answers.add(answer6);

        answerList = service.findTestAnswersBySubject(subject.getId());
        for (TestAnswer t : answerList){
            assertTrue("Getting unrelated answers while trying to get all answers from a certain subject",answers.contains(t));
        }
        for (TestAnswer t : answers){
            assertTrue("Didn't get all the answers for a specific subject",answerList.contains(t));
        }


        service.deleteAllTestAnswersForQuestion(question1.getId());
        service.deleteAllTestAnswersForQuestion(question.getId());

        managerService.deleteTestManager(manager.getId());

        testService.deleteTestForTestManager(test.getId());
        testService.deleteTestForTestManager(test2.getId());

        blockService.deleteTestBlock(block.getId());

        subjectService.deleteTestSubject(subject.getId());
    }





}