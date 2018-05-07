package cognitivity.web.app;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

/**
 * Created by ophir on 07/05/18.
 */
@Configuration
public class ApplicationInsightsConfiguration {

    // This should be obscured! Secret key for Azure's resources.
    public static final String INSTRUMENTATION_KEY = "bf85656a-0c3e-4a2d-a744-fdffa181700b";

    public void configure() {
        TelemetryConfiguration configuration = TelemetryConfiguration.getActive();
        configuration.setInstrumentationKey(INSTRUMENTATION_KEY);
    }

    //Initialize AI TelemetryConfiguration via Spring Beans
    @Bean
    public String telemetryConfig() {
        String telemetryKey = System.getenv("APPLICATION_INSIGHTS_IKEY");
        if (telemetryKey != null) {
            TelemetryConfiguration.getActive().setInstrumentationKey(telemetryKey);
        }
        return telemetryKey;
    }

    //Set AI Web Request Tracking Filter
    @Bean
    public FilterRegistrationBean aiFilterRegistration(@Value("${spring.application.name:application}") String applicationName) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WebRequestTrackingFilter(applicationName));
        registration.setName("webRequestTrackingFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        return registration;
    }

    //Set up AI Web Request Tracking Filter
    @Bean(name = "WebRequestTrackingFilter")
    public Filter webRequestTrackingFilter(@Value("${spring.application.name:application}") String applicationName) {
        return new WebRequestTrackingFilter(applicationName);
    }
}
