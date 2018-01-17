package cognitivity.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ophir on 03/01/18.
 */
@SpringBootApplication(scanBasePackages = "cognitivity")
public class CognitivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CognitivityApplication.class, args);
    }
}
