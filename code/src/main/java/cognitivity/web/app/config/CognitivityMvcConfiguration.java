package cognitivity.web.app.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by ophir on 23/11/17.
 */


@EnableWebMvc
@Configuration
@ComponentScan("cognitivity")
@EnableTransactionManagement
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

    //TODO: not working
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

    /*********************************************  Hibernate Initialization *********************************************/

    @Bean
    @Autowired
    public LocalSessionFactoryBean hibernateSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("cognitivity.entities");
        sessionFactory.setHibernateProperties(additionalProperties());
        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Cognitivity");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    private static Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("current_session_context_class","thread");
        return properties;
    }
}
