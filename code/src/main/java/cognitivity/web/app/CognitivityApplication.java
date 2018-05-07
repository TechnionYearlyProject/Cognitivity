package cognitivity.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Created by ophir on 03/01/18.
 */
@SpringBootApplication(scanBasePackages = {"cognitivity", "com.microsoft.applicationinsights.web.spring"},
        exclude = HibernateJpaAutoConfiguration.class)
public class CognitivityApplication {
    public static void main(String[] args) {
        new ApplicationInsightsConfiguration().configure();
        SpringApplication.run(CognitivityApplication.class, args);
    }
}
