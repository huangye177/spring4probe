package com.yummynoodlebar.config;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yummynoodlebar.persistence.repository.OrdersRepository;

@Configuration
@EnableJpaRepositories(basePackages = "com.yummynoodlebar.persistence.repository",
        includeFilters = @ComponentScan.Filter(value = { OrdersRepository.class }, type = FilterType.ASSIGNABLE_TYPE))
@EnableTransactionManagement
public class JPAConfiguration
{

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
        factory.setPackagesToScan("com.yummynoodlebar.persistence.domain");
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
