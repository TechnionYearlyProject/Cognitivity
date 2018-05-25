package cognitivity.web.app.config;

import cognitivity.services.fileLoader.ITestReader;
import cognitivity.services.fileLoader.TestReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.function.Function;

/**
 * Created by ophir on 26/5/18.
 */

@Configuration
@EnableWebMvc
@ComponentScan(value = "cognitivity.services")
public class FactoryBeanConfiguration {

    @Bean
    public Function<String, ITestReader> testReaderSupplier() {
        return TestReader::new;
    }

}
