package config;

import cognitivity.controllers.*;
import cognitivity.dao.*;
import cognitivity.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ophir on 17/01/18.
 */
@Configuration
@ComponentScan("cognitivity")
public class IntegrationTestContextConfiguration {

    @Autowired
    private CognitiveTestDAO cognitiveTestDAO;

    @Autowired
    private TestAnswerDAO testAnswerDAO;

    @Autowired
    private TestManagerDAO testManagerDAO;

    @Autowired
    private TestQuestionDAO testQuestionDAO;

    @Autowired
    private TestSubjectDAO testSubjectDAO;

    @Autowired
    private TestBlockDAO testBlockDAO;


    @Bean
    public CognitiveTestController cognitiveTestController() {
        return new CognitiveTestController(new CognitiveTestService(cognitiveTestDAO, testBlockDAO, testQuestionDAO));
    }

    @Bean
    public TestAnswerController testAnswerController() {
        return new TestAnswerController(new TestAnswerService(testAnswerDAO, testSubjectDAO));
    }

    @Bean
    public TestManagerController testManagerController() {
        return new TestManagerController(new TestManagerService(testManagerDAO, cognitiveTestDAO));
    }

    @Bean
    public TestQuestionController testQuestionController() {
        return new TestQuestionController(new QuestionService(testQuestionDAO, testAnswerDAO, cognitiveTestDAO, testManagerDAO));
    }

    @Bean
    public TestSubjectController testSubjectController() {
        return new TestSubjectController(new TestSubjectService(testSubjectDAO));
    }

}
