package com.hh.contractstestsadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ContractsDao;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.service.ValidationService;
import io.minio.MinioClient;
import io.swagger.jaxrs.config.BeanConfig;
import java.util.Properties;
import javax.sql.DataSource;

import com.hh.contractstestsadmin.service.StatusService;
import com.hh.contractstestsadmin.service.StandValidationService;
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
@PropertySource("classpath:application.properties")
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

  @Value("${spring.minio.url}")
  private String minioUrl;

  @Value("${spring.minio.access-key}")
  private String minioAccessKey;

  @Value("${spring.minio.secret-key}")
  private String minioSecretKey;

  @Value("${release.stand.name}")
  private String releaseName;

  @Bean
  public BeanConfig configureSwagger() {
    BeanConfig swaggerConfigBean = new BeanConfig();
    swaggerConfigBean.setTitle("Contract tests backend");
    swaggerConfigBean.setSchemes(new String[]{"http"});
    swaggerConfigBean.setBasePath("/");
    swaggerConfigBean.setVersion("1.1.0");
    swaggerConfigBean.setResourcePackage("com.hh.contractstestsadmin.resource");
    swaggerConfigBean.setPrettyPrint(true);
    swaggerConfigBean.setScan(true);
    return swaggerConfigBean;
  }

  @Bean
  public ReleaseVersionDao releaseVersionDao() {
    return new ReleaseVersionDao();
  }

  @Bean
  public StatusService statusService(ContractsDao contractsDao, ReleaseVersionDao releaseVersionDao,
                                     ObjectMapper objectMapper) {
    return new StatusService(contractsDao, releaseName, releaseVersionDao, objectMapper);
  }

  @Bean
  public ValidationService validationService(ValidationDao validationDao, ReleaseVersionDao releaseVersionDao) {
    return new ValidationService(validationDao, releaseVersionDao);
  }

  @Bean
  public StandValidationService standValidationService(ContractsDao contractsDao, ValidationService validationService) {
    return new StandValidationService(contractsDao, validationService);
  }

  @Bean
  public ValidationDao validationDao(LocalSessionFactoryBean sessionFactoryBean) {
    return new ValidationDao(sessionFactoryBean.getObject());
  }

  @Bean
  public ServiceDao serviceDao(LocalSessionFactoryBean sessionFactoryBean) {
    return new ServiceDao(sessionFactoryBean.getObject());
  }

  @Bean
  public ValidationInfoDao validationInfoDao(LocalSessionFactoryBean sessionFactoryBean) {
    return new ValidationInfoDao(sessionFactoryBean.getObject());
  }

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(minioUrl)
        .credentials(minioAccessKey, minioSecretKey)
        .build();
  }

  @Bean
  public ContractsDao contractsDao() {
    return new ContractsDao();
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
  public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
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
