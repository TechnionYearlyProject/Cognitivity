package cognitivity.web.app.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by ophir on 23/11/17.
 */



@Configuration
@EnableWebMvc
@ComponentScan(value = "cognitivity")
@Import(HibernateBeanConfiguration.class)
@PropertySource(value="application.properties")
public class CognitivityMvcConfiguration {

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

    @Value("${db.port}")
    String serverPort;

    @Value("${db.name}")
    String databaseName;

    @Value("${db.userName}")
    String userName;

    // todo: maybe it will be better to pass sensitive parameters by command line?
    @Value("${db.password}")
    String password;

    @Value("${db.host_name}")
    String serverName;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + serverName + ":" + serverPort + "/" + databaseName + "?serverTimezone=UTC&useSSL=false&requireSSL=false");
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    private static Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("current_session_context_class", "thread");
        return properties;
    }
}
