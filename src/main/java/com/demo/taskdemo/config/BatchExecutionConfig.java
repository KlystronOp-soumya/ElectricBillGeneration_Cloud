package com.demo.taskdemo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = "classpath:db-config.properties")
public class BatchExecutionConfig {

	private Environment env;

	public BatchExecutionConfig(final Environment env) {
		// TODO Auto-generated constructor stub
		this.env = env;
	}

	/* Configure the Database */
	@Bean(name = "dataSource")
	@Primary
	public DataSource dataSource() {
		final HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setAutoCommit(true);
		hikariDataSource.setPoolName("BillBatchProcessingPool");
		hikariDataSource.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
		hikariDataSource.setSchema("CYOLASBCOM");
		hikariDataSource.setMaximumPoolSize(15);
		hikariDataSource.setDataSourceProperties(dataSourceProps());
		hikariDataSource.setDriverClassName(this.env.getProperty("mysql.driver"));
		hikariDataSource.setJdbcUrl(this.env.getProperty("mysql.url"));
		hikariDataSource.setUsername(this.env.getProperty("mysql.user"));
		hikariDataSource.setPassword(this.env.getProperty("mysql.password"));

		return hikariDataSource;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setDatabaseProductName("MYSQL");
		jdbcTemplate.setExceptionTranslator(sqlErrorCodeSQLExceptionTranslator(dataSource));
		jdbcTemplate.setFetchSize(Integer.MIN_VALUE);

		return jdbcTemplate;
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
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceUnitName("billProcessingPersistenceUnit");
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("com.demo.taskdemo.entities");
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabasePlatform("MYSQL");
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

		return entityManagerFactoryBean;
	}

	/*
	 * @Bean public PersistenceAnnotationBeanPostProcessor
	 * persistenceAnnotationBeanPostProcessor() { return new
	 * PersistenceAnnotationBeanPostProcessor(); }
	 */

	@Bean(name = "jpaTransactionManager")
	public PlatformTransactionManager jpaTransactionManager(
			final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager;
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
	public PlatformTransactionManager transactionManager() {
		final JdbcTransactionManager transactionManager = new JdbcTransactionManager();
		transactionManager.setDataSource(dataSource());
		transactionManager.setDatabaseProductName("MySQL");
		transactionManager.setRollbackOnCommitFailure(true);
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
	@Primary
	public JobRepository jobRepository() throws Exception {

		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		jobRepositoryFactoryBean.setDataSource(dataSource());
		jobRepositoryFactoryBean.setTransactionManager(transactionManager());
		jobRepositoryFactoryBean.setTablePrefix("BATCH_");
		jobRepositoryFactoryBean.afterPropertiesSet();

		return jobRepositoryFactoryBean.getObject();
	}

	@Bean(name = "jobLauncher")
	@Primary
	public SimpleJobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		simpleJobLauncher.setJobRepository(this.jobRepository());
		simpleJobLauncher.afterPropertiesSet();

		return simpleJobLauncher;
	}

	/* Data initialization */
	@Bean("initDBBean")
	public DatabasePopulator populateDatabase() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		Resource resource = new ClassPathResource("scripts/create-db.sql");
		databasePopulator.addScript(resource);
		databasePopulator.setSqlScriptEncoding("UTF-8");

		return databasePopulator;
	}

}
