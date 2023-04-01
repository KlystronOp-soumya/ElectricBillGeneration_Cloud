package com.demo.taskdemo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration(proxyBeanMethods = true, value = "batchExecutionConfiguration")
@PropertySource(value = "classpath:db-config.properties", ignoreResourceNotFound = false)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.demo.taskdemo.repo")
public class BillProcessingBatchExecutionConfig implements EnvironmentAware {

	private transient Environment env;

	@Bean("dataSource")
	@Primary
	public DataSource pooledDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDataSource(getDataSource());
		dataSource.setAutoCommit(true);

		dataSource.setDataSourceProperties(dataSourceProps());

		dataSource.setMaximumPoolSize(50);
		dataSource.setPoolName("billAmountProcessing-HikariPool");
		return dataSource;

	}

	protected DataSource getDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("mysql.driver"));
		dataSource.setUrl(env.getProperty("mysql.url"));
		dataSource.setSchema("CYOLASBCOM");
		dataSource.setUsername(env.getProperty("mysql.user"));
		dataSource.setPassword(env.getProperty("mysql.password"));
		return dataSource;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setDatabaseProductName("MYSQL");
		jdbcTemplate.setExceptionTranslator(sqlErrorCodeSQLExceptionTranslator(dataSource));
		jdbcTemplate.setFetchSize(100);
		return jdbcTemplate;
	}

	@Bean("namedParameterJdbcTemplate")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate;
	}

	protected Properties dataSourceProps() {
		final Properties dataSourceProperties = new Properties();

		dataSourceProperties.put("cachePrepStmts", true);
		dataSourceProperties.put("prepStmtCacheSize", 250);
		dataSourceProperties.put("prepStmtCacheSqlLimit", 2048);
		dataSourceProperties.put("useServerPrepStmts", true);
		dataSourceProperties.put("useLocalSessionState", true);
		dataSourceProperties.put("rewriteBatchedStatements", true);
		dataSourceProperties.put("cacheResultSetMetadata", true);
		dataSourceProperties.put("cacheServerConfiguration", true);
		dataSourceProperties.put("elideSetAutoCommits", true);
		dataSourceProperties.put("maintainTimeStats", false);

		return dataSourceProperties;
	}

	/* Configure persistence layer */
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceUnitName("billProcessingPersistenceUnit");
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("com.demo.taskdemo.entities");
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabasePlatform("MYSQL");
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

		return entityManagerFactoryBean;
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean(name = "jpaTransactionManager")
	@Scope("singleton")
	public PlatformTransactionManager jpaTransactionManager(
			final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		// transactionManager.setDefaultTimeout(600000);
		return transactionManager;
	}

	// added on 17/02/2023
	@Bean("jpaDialect")
	public JpaDialect jpaDialect() {
		JpaDialect jpaDialect = new HibernateJpaDialect();
		return jpaDialect;
	}

	@Bean("sqlErrorCodeSQLExceptionTranslator")
	public SQLExceptionTranslator sqlErrorCodeSQLExceptionTranslator(final DataSource dataSource) {
		final SQLErrorCodeSQLExceptionTranslator exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator();

		exceptionTranslator.setDatabaseProductName("MySQL");
		exceptionTranslator.setDataSource(dataSource);
		return exceptionTranslator;
	}

	@Bean(name = "transactionManager")
	@Primary
	public PlatformTransactionManager transactionManager(final DataSource dataSource) {
		final JdbcTransactionManager transactionManager = new JdbcTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setDatabaseProductName("MySQL");
		transactionManager.setRollbackOnCommitFailure(true);
		// transactionManager.setDefaultTimeout(600000);
		transactionManager.afterPropertiesSet();

		return transactionManager;

	}

	protected Properties jpaProperties() {
		final Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		// do not need these if Hikari is being used
		// props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		// props.put("hibernate.connection.provider_class",
		// "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
		props.put("hibernate.connection.isolation", "REPEATABLE_READ");
		props.put("hibernate.show_sql", true);
		props.put("hibernate.format_sql", true);
		props.put("hibernate.hbm2ddl.auto", "none");
		props.put("hibernate.cache.use_second_level_cache", false);
		props.put("hibernate.cache.use_query_cache", false);
		props.put("hibernate.generate_statistics", true);

		return props;

	}

	@Bean(name = "jobRepository")
	public JobRepository jobRepository(final DataSource dataSource) throws Exception {

		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager(dataSource));
		jobRepositoryFactoryBean.setTablePrefix("BATCH_");
		jobRepositoryFactoryBean.afterPropertiesSet();

		return jobRepositoryFactoryBean.getObject();
	}

	// added 18/03/2023
	/*
	 * @Bean("jobOperator") public JobOperator jobOperator(final JobExplorer
	 * jobExplorer, final JobRepository jobRepository, final JobRegistry
	 * jobRegistry) throws Exception { final SimpleJobOperator jobOperator = new
	 * SimpleJobOperator();
	 * 
	 * jobOperator.setJobExplorer(jobExplorer);
	 * jobOperator.setJobRepository(jobRepository);
	 * jobOperator.setJobRegistry(jobRegistry);
	 * jobOperator.setJobLauncher(jobLauncher()); return jobOperator;
	 * 
	 * }
	 */

	@Bean(name = "jobLauncher")
	public SimpleJobLauncher jobLauncher(final DataSource dataSource) throws Exception {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		simpleJobLauncher.setJobRepository(this.jobRepository(dataSource));
		simpleJobLauncher.afterPropertiesSet();

		return simpleJobLauncher;
	}

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		this.env = environment;
	}

	/* Data initialization */
//	@Bean("initDBBean")
//	public DatabasePopulator populateDatabase() {
//		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//		Resource resource = new ClassPathResource("scripts/create-db.sql");
//		databasePopulator.addScript(resource);
//		databasePopulator.setSqlScriptEncoding("UTF-8");
//
//		return databasePopulator;
//	}

	/* Temporarily added */
	/*
	 * @Bean public TaskExplorer taskExplorer(final DataSource dataSource) { return
	 * new SimpleTaskExplorer(new TaskExecutionDaoFactoryBean(dataSource)); }
	 * 
	 * @Bean public TaskRepository taskRepository(final DataSource dataSource) {
	 * return new SimpleTaskRepository(new TaskExecutionDaoFactoryBean(dataSource));
	 * }
	 * 
	 * @Bean("customTaskConfigurer")
	 * 
	 * @Primary public TaskConfigurer taskConfigurer(final DataSource dataSource) {
	 * ElectricBillProcessingTaskConfig customTask = new
	 * ElectricBillProcessingTaskConfig(dataSource, transactionManager(dataSource),
	 * new TaskExecutionDaoFactoryBean(dataSource)); return customTask; }
	 * 
	 */

}
