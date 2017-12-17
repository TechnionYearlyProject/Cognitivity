package cognitivity.web.app;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cognitivity.security")
public class WebAppInitializer implements WebApplicationInitializer {

    private static final String CONFIG_LOCATION = "cognitivity.web.app.config";
    private static final String SERVLET_MAPPING = "/cognitivity/";

    public void onStartup(ServletContext servletContext) throws ServletException {

        System.out.println("***** Initializing Application for " + servletContext.getServerInfo() + " *****");

        // Create ApplicationContext
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setConfigLocation(CONFIG_LOCATION);

        // Add the servlet mapping manually and make it initialize automatically
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("mvc-dispatcher", dispatcherServlet);

        servlet.addMapping(SERVLET_MAPPING);
        servlet.setAsyncSupported(true);
        servlet.setLoadOnStartup(1);
    }
}
