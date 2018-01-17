package cognitivity.web.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by ophir on 23/11/17.
 */


@EnableWebMvc
@Configuration
@ComponentScan(value = "cognitivity")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Import(value = HibernateBeanConfiguration.class)
public class CognitivityMvcConfiguration/* extends WebMvcConfigurationSupport *//* implements BeanDefinitionRegistryPostProcessor */ {

}
