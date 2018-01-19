package config;

import cognitivity.controllers.*;
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
    private CognitiveTestService cognitiveTestService;

    @Autowired
    private TestAnswerService testAnswerService;

    @Autowired
    private TestManagerService testManagerService;

    @Autowired
    private QuestionService testQuestionService;

    @Autowired
    private TestSubjectService testSubjectService;


    @Bean
    public CognitiveTestController cognitiveTestController() {
        return new CognitiveTestController(cognitiveTestService);
    }

    @Bean
    public TestAnswerController testAnswerController() {
        return new TestAnswerController(testAnswerService);
    }

    @Bean
    public TestManagerController testManagerController() {
        return new TestManagerController(testManagerService);
    }

    @Bean
    public TestQuestionController testQuestionController() {
        return new TestQuestionController(testQuestionService);
    }

    @Bean
    public TestSubjectController testSubjectController() {
        return new TestSubjectController(testSubjectService);
    }

}
