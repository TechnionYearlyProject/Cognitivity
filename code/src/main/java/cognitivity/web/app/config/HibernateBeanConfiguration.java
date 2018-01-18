package cognitivity.web.app.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class HibernateBeanConfiguration {
//
//    @Bean
//    @Autowired
//    public LocalSessionFactoryBean hibernateSessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("cognitivity.entities");
//        sessionFactory.setHibernateProperties(additionalProperties());
//        return sessionFactory;
//    }
//
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory);
//
//        return transactionManager;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/Cognitivity?serverTimezone=UTC");
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");
//        return dataSource;
//    }
//
//    private static Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("current_session_context_class", "thread");
//        return properties;
//    }
}
