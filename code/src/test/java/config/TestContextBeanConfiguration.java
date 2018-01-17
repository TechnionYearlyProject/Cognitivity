package config;

import cognitivity.dao.*;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;
import cognitivity.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ophir on 06/01/18.
 */
@Configuration
public class TestContextBeanConfiguration {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CognitiveTestService cognitiveTestService() {
        return Mockito.mock(CognitiveTestService.class);
    }

    @Bean
    public TestAnswerService testAnswerService() {
        return Mockito.mock(TestAnswerService.class);
    }

    @Bean
    public QuestionService testQuestionService() {
        return Mockito.mock(QuestionService.class);
    }

    @Bean
    public TestManagerService testManagerService() {
        return Mockito.mock(TestManagerService.class);
    }

    @Bean
    public TestSubjectService testSubjectService() {
        return Mockito.mock(TestSubjectService.class);
    }

    @Bean
    public EmailRegistrationService emailRegistrationService() {
        return Mockito.mock(EmailRegistrationService.class);
    }

    @Bean
    public CognitiveTestDAOimpl cognitiveTestDAOimpl() {
        CognitiveTestDAOimpl mock = Mockito.mock(CognitiveTestDAOimpl.class);
        return mock;
    }

    @Bean
    public TestQuestionDAOimpl testQuestionDAOimpl() {
        TestQuestionDAOimpl mock = Mockito.mock(TestQuestionDAOimpl.class);
        return mock;
    }

    @Bean
    public TestAnswerDAOimpl testAnswerDAOimpl() {
        TestAnswerDAOimpl mock = Mockito.mock(TestAnswerDAOimpl.class);
        return mock;
    }

    @Bean
    public TestBlockDAOimpl testBlockDAOimpl() {
        TestBlockDAOimpl mock = Mockito.mock(TestBlockDAOimpl.class);
        return mock;
    }

    @Bean
    public TestManagerDAOimpl testManagerDAOimpl() {
        TestManagerDAOimpl mock = Mockito.mock(TestManagerDAOimpl.class);
        return mock;
    }

    @Bean
    public TestSubjectDAOimpl testSubjectDAOimpl() {
        TestSubjectDAOimpl mock = Mockito.mock(TestSubjectDAOimpl.class);
        return mock;
    }

}