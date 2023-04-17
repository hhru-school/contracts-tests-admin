package com.hh.contractstestsadmin.config;

import com.hh.contractstestsadmin.dao.VerificationHistoryDao;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class AppConfig {

  @Value("${hibernate.driver.class}")
  private String driverClassName;
  @Value("${hibernate.connection.url}")
  private String jdbcUrl;

  @Value("${hibernate.connection.username}")
  private String userName;

  @Value("${hibernate.connection.password}")
  private String password;

  @Value("${hibernate.dialect}")
  private String hibernateDialect;

  @Value("${hibernate.show.sql}")
  private String hibernateShowSql;

  @Bean
  public VerificationHistoryDao verificationHistoryDao(LocalSessionFactoryBean sessionFactoryBean) {
    return new VerificationHistoryDao(sessionFactoryBean.getObject());
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(userName);
    dataSource.setPassword(password);
    return dataSource;
  }

  private Properties hibernateProperties() {
    Properties hibernateProperties = new Properties();
    hibernateProperties.put(Environment.DIALECT, hibernateDialect);
    hibernateProperties.put(Environment.SHOW_SQL, hibernateShowSql);
    return hibernateProperties;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.hh.contractstestsadmin.model");
    sessionFactory.setHibernateProperties(hibernateProperties());

    return sessionFactory;
  }

  @Bean
  public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactoryBean) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactoryBean.getObject());
    return transactionManager;
  }

}
