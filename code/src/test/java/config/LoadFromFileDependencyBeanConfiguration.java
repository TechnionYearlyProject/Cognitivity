package config;

import cognitivity.dao.*;
import cognitivity.services.LoadFromFileService;
import cognitivity.services.fileLoader.TestReader;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ophir on 25/05/18.
 */
@Configuration
public class LoadFromFileDependencyBeanConfiguration {

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

    @Bean
    public LoadFromFileService loadFromFileService(TestQuestionDAO testQuestionDAO,
                                                   CognitiveTestDAO cognitiveTestDAO,
                                                   TestBlockDAO testBlockDAO,
                                                   TestManagerDAO testManagerDAO) {
        return new LoadFromFileService(
                testQuestionDAO,
                cognitiveTestDAO,
                testBlockDAO,
                testManagerDAO,
                () -> new TestReader("data")
        );
    }
}
