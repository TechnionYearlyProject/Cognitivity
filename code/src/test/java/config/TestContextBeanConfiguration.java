package config;

import cognitivity.dao.*;
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
    public LoadFromFileService loadFromFileService() {
        return Mockito.mock(LoadFromFileService.class);
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
    public CognitiveTestDAO cognitiveTestDAO() {
        return Mockito.mock(CognitiveTestDAO.class);
    }

    @Bean
    public TestQuestionDAO testQuestionDAO() {
        return Mockito.mock(TestQuestionDAO.class);
    }

    @Bean
    public TestAnswerDAO testAnswerDAO() {
        return Mockito.mock(TestAnswerDAO.class);
    }

    @Bean
    public TestBlockDAO testBlockDAO() {
        return Mockito.mock(TestBlockDAO.class);
    }

    @Bean
    public TestManagerDAO testManagerDAO() {
        return Mockito.mock(TestManagerDAO.class);
    }

    @Bean
    public TestSubjectDAO testSubjectDAO() {
        return Mockito.mock(TestSubjectDAO.class);
    }

}
