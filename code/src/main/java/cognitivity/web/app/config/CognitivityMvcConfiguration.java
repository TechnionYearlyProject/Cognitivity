package cognitivity.web.app.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;


/**
 * Created by ophir on 23/11/17.
 */


@EnableWebMvc
@Configuration
@ComponentScan("cognitivity")
public class CognitivityMvcConfiguration implements BeanDefinitionRegistryPostProcessor {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/js/**")
                .addResourceLocations("/resources/static/js/");
        registry.addResourceHandler("/resources/static/css/**")
                .addResourceLocations("/resources/static/css/");
        registry.addResourceHandler("/resources/static/views/**")
                .addResourceLocations("/resources/static/views/");
        registry.addResourceHandler("/resources/static/**")
                .addResourceLocations("/resources/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".ts");
        return resolver;
    }

    public SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
        SessionFactory unwrap = entityManagerFactory.unwrap(SessionFactory.class);
        if (unwrap == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return unwrap;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        /*Reflections reflections = new Reflections("cognitivity.controllers");
        Set<Class<? extends AbstractRestController>> classes = reflections.getSubTypesOf(AbstractRestController.class);
        for (Class<?> clazz : classes) {
            if (!clazz.getName().endsWith("Controller")) continue;
            if (Modifier.isAbstract(clazz.getModifiers())) continue;
            System.out.println(clazz.getName());
            BeanDefinition beanDefinition = new RootBeanDefinition(clazz, Autowire.BY_TYPE.value(), true);
            beanDefinitionRegistry.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
        }*/
        // BeanDefinition cognitiveTestController = new RootBeanDefinition(CognitiveTestController.class, Autowire.BY_TYPE.value(), true);
        // beanDefinitionRegistry.registerBeanDefinition("cognitiveTestController", cognitiveTestController);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
