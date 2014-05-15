package io.spring.jdbctx;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * @EnableTransactionManagement activates Spring’s seamless transaction features, 
 * which makes @Transactional function
 */
@Configuration
@EnableTransactionManagement
public class JDBCTxConfig
{
	@Bean
    BookingService bookingService() {
        return new BookingService();
    }

	/*
	 * SimpleDriverDataSource is a convenience class and is NOT intended for production. 
	 * For production, you usually want some sort of JDBC connection pool to handle multiple 
	 * requests coming in simultaneously
	 */
//    @Bean
//    DataSource dataSource() {
//        return new SimpleDriverDataSource() {{
//            setDriverClass(org.h2.Driver.class);
//            setUsername("sa");
//            setUrl("jdbc:h2:mem:test_mem");
//            setPassword("");
//        }};
//    }

	/*
	 * The BOOKINGS table has two constraints on the first_name column:
	 * 
	 * Names cannot be longer than five characters.
	 * Names cannot be null.
	 */
    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("Creating tables");
        jdbcTemplate.execute("drop table BOOKINGS if exists");
        jdbcTemplate.execute("create table BOOKINGS(" +
                "ID serial, FIRST_NAME varchar(5) NOT NULL)");
        return jdbcTemplate;
    }
    
    /*
     * creates a new H2 instance within the same ApplicationContext and provides
     * a DataSource interface to it, usable by JPA
     */
    @Bean
    public DataSource dataSource() throws SQLException
    {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    /*
     * creates the EntityManagerFactory, a class is responsible for creating the
     * EntityManager, and is JPA Provider specific
     * 
     * The EntityManagerFactory is responsible for identifying the JPA Entities
     * to be made available, the classes to be treated as database mapping/
     * persistence beans
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() throws SQLException
    {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("io.spring");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /*
     * creates the core class of JPA: EntityManager, which is the public
     * interface of JPA, providing methods to persist, delete, update and query
     * 
     * IMPORTANT:
     * 
     * Argument and potential error (fixed by using @PersistenceContext) from
     * the tutorial
     * (http://stackoverflow.com/questions/22301426/no-qualifying-bean
     * -of-type-javax-persistence-entitymanager):
     * 
     * Spring is now smart enough (especially with @EnableJpaRepositories) to
     * create EMs as necessary from the factory
     * 
     * The EntityManager interface belongs to JPA and is implemented by JPA
     * providers, not Spring, and it has its own injection annotation:
     * 
     * @PersistenceContext.
     * 
     * EntityManager objects are transaction-scoped and should not be exposed as
     * beans
     */
    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory)
    {
        return entityManagerFactory.createEntityManager();
    }

    /*
     * integrates with the declarative Transaction Management features of Spring
     * to initialize the JPA transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException
    {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator()
    {
        return new HibernateExceptionTranslator();
    }
}
