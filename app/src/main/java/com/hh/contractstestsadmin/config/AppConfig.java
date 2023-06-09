package com.hh.contractstestsadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.contractstestsadmin.dao.ErrorTypeDao;
import com.hh.contractstestsadmin.dao.minio.StandsDao;
import com.hh.contractstestsadmin.dao.minio.mapper.ConsumerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ProducerDataMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceListMapper;
import com.hh.contractstestsadmin.dao.minio.mapper.ServiceMapper;
import com.hh.contractstestsadmin.dao.ReleaseVersionDao;
import com.hh.contractstestsadmin.dao.ServiceDao;
import com.hh.contractstestsadmin.dao.ValidationDao;
import com.hh.contractstestsadmin.dao.ValidationInfoDao;
import com.hh.contractstestsadmin.service.CustomEntityService;
import com.hh.contractstestsadmin.service.builder.ValidationBuilder;
import com.hh.contractstestsadmin.service.ValidationService;
import com.hh.contractstestsadmin.validator.service.ValidatorService;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.hh.contract.validator.service.ContractsValidator;
import ru.hh.contract.validator.validation.OpenApiInteractionValidatorFactory;
import ru.hh.contract.validator.validation.ValidationContextProvider;

@Configuration
@PropertySource("classpath:hibernate.properties")
@PropertySource("classpath:minio.properties")
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

  @Value("${minio.url}")
  private String minioUrl;

  @Value("${minio.external.url}")
  private String externalBaseUrl;

  @Value("${minio.access-key}")
  private String minioAccessKey;

  @Value("${minio.secret-key}")
  private String minioSecretKey;

  @Value("${minio.release.stand.name}")
  private String minioReleaseName;

  @Value("${minio.producer.artefact.type}")
  private String producerArtefactType;

  @Value("${minio.consumer.artefact.type}")
  private String consumerArtefactType;

  @Value("${minio.artefact.url.expiration.period}")
  private String artefactUrlExpirationPeriod;

  @Bean
  public BeanConfig configureSwagger() {
    BeanConfig swaggerConfigBean = new BeanConfig();
    swaggerConfigBean.setTitle("Contract tests backend");
    swaggerConfigBean.setSchemes(new String[]{"http"});
    swaggerConfigBean.setBasePath("/");
    swaggerConfigBean.setVersion("1.1.1");
    swaggerConfigBean.setResourcePackage("com.hh.contractstestsadmin.resource");
    swaggerConfigBean.setPrettyPrint(true);
    swaggerConfigBean.setScan(true);
    return swaggerConfigBean;
  }

  @Bean
  public TaskExecutor executorService() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(1);
    taskExecutor.setMaxPoolSize(1);
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    taskExecutor.setAwaitTerminationSeconds(180);
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Bean
  public ReleaseVersionDao releaseVersionDao() {
    return new ReleaseVersionDao();
  }

  @Bean
  public StatusService statusService(StandsDao standsDao, ReleaseVersionDao releaseVersionDao) {
    return new StatusService(standsDao, minioReleaseName, releaseVersionDao);
  }

  @Bean
  public ErrorTypeDao errorTypeDao(LocalSessionFactoryBean sessionFactoryBean) {
    return new ErrorTypeDao(sessionFactoryBean.getObject());
  }

  @Bean
  public ValidationService validationService(
      ValidationDao validationDao,
      ReleaseVersionDao releaseVersionDao,
      ValidationInfoDao validationInfoDao,
      ServiceDao serviceDao,
      ErrorTypeDao errorTypeDao,
      ValidationBuilder validationBuilder
  ) {
    return new ValidationService(
        validationDao,
        releaseVersionDao,
        validationInfoDao,
        serviceDao,
        errorTypeDao,
        minioReleaseName,
        validationBuilder
    );
  }

  @Bean
  public ValidationBuilder validationBuilder(StandsDao standsDao) {
    return new ValidationBuilder(standsDao, minioReleaseName);
  }

  @Bean
  public StandValidationService standValidationService(
      StandsDao standsDao,
      ValidationService validationService,
      TaskExecutor executorService,
      ValidatorService validatorService
  ) {
    return new StandValidationService(standsDao, validationService, executorService, validatorService);
  }

  @Bean
  public ValidatorService validatorService(StandsDao standsDao, ObjectMapper objectMapper, ContractsValidator contractsValidator) {
    return new ValidatorService(standsDao, objectMapper, contractsValidator, minioReleaseName);
  }

  @Bean
  public CustomEntityService customEntityService(ErrorTypeDao errorTypeDao){
    return new CustomEntityService(errorTypeDao);
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
  public StandsDao standsDao(MinioClient minioClient, ServiceListMapper serviceListMapper) {
    return new StandsDao(minioClient, minioProperties(), serviceListMapper);
  }

  @Bean
  public ServiceListMapper serviceListMapper(ServiceMapper serviceMapper) {
    return new ServiceListMapper(minioProperties(), serviceMapper);
  }

  @Bean
  public ServiceMapper serviceMapper(ConsumerDataMapper consumerDataMapper, ProducerDataMapper producerDataMapper) {
    return new ServiceMapper(minioProperties(), consumerDataMapper, producerDataMapper);
  }

  @Bean
  public ConsumerDataMapper consumerDataMapper() {
    return new ConsumerDataMapper();
  }

  @Bean
  public ProducerDataMapper producerDataMapper() {
    return new ProducerDataMapper();
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

  private Properties minioProperties() {
    Properties minioProperties = new Properties();
    minioProperties.put("minio.base.url", minioUrl);
    minioProperties.put("minio.external.base.url", externalBaseUrl);
    minioProperties.put("minio.consumer.artefact.type", consumerArtefactType);
    minioProperties.put("minio.producer.artefact.type", producerArtefactType);
    minioProperties.put("minio.artefact.url.expiration.period", artefactUrlExpirationPeriod);
    return minioProperties;
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

  @Bean
  public ValidationContextProvider validationContextProvider() {
    return new ValidationContextProvider();
  }

  @Bean
  public OpenApiInteractionValidatorFactory openApiInteractionValidatorFactory(ValidationContextProvider validationContextProvider) {
    return new OpenApiInteractionValidatorFactory(validationContextProvider);
  }

  @Bean
  public ContractsValidator contractsValidator(
      ValidationContextProvider validationContextProvider, OpenApiInteractionValidatorFactory
      openApiInteractionValidatorFactory
  ) {
    return new ContractsValidator(validationContextProvider, openApiInteractionValidatorFactory);
  }
}
