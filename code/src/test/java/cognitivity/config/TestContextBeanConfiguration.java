package cognitivity.config;

import cognitivity.services.CognitiveTestService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ophir on 06/01/18.
 */
@Configuration
public class TestContextBeanConfiguration {

    @Bean
    public CognitiveTestService cognitiveTestService() {
        return Mockito.mock(CognitiveTestService.class);
    }
}
