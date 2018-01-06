package cognitivity.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ophir on 03/01/18.
 */
@SpringBootApplication
public class CognitivityApplication {

    public static void main(String[] args) {
        System.out.println(new Object() {
        }.getClass().getEnclosingClass().getName());
        SpringApplication.run(CognitivityApplication.class, args);
    }
}
