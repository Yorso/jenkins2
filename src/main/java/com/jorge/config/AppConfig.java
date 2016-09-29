/**
 * This is a configuration class
 * 
 */

package com.jorge.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration // This declares it as a Spring configuration class
@EnableWebMvc // This enables Spring's ability to receive and process web requests. Necessary for interceptors too.
@EnableTransactionManagement // Reverting incomplete database modifications using transactions
							 // Some database modifications involve several SQL queries, for example, inserting an object with
							 // attributes spread across several tables. If one of the queries fails, we would want to undo any
							 // previous ones that were successful
							 // 1. Add @EnableTransactionManagement to the Spring configuration class
							 // 2. Add a DataSourceTransactionManager bean to the Spring configuration
							 // 3. Annotate the DAO class with @Transactional
@ComponentScan(basePackages = { "com.jorge.controller", "com.jorge.dao" }) // This scans the com.jorge.controller and com.jorge.dao packages for Spring components

// @Import({ DatabaseConfig.class, SecurityConfig.class }) => // If you are using a Spring application without a 'ServletInitializer' class,
															  // you can include other configuration classes from your primary configuration class

public class AppConfig{

	/*
	 * If we aren't going to use Tiles, uncomment jspViewResolver() method and
	 * comment tilesConfigurer() and tilesViewResolver() methods
	 *
	 */
	 @Bean 
	 public ViewResolver jspViewResolver(){ 
		 InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		 resolver.setViewClass(JstlView.class);
		 resolver.setPrefix("/WEB-INF/jsp/"); // These folders must be created on /src/main/webapp/ 
		 resolver.setSuffix(".jsp"); return resolver; 
	 }
	
	/***************
	 *  DATABASES  *
	 ***************/ 
	// Database connection details
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
		dataSource.setUsername("user1");
		dataSource.setPassword("user1pass");
		return dataSource;
	}
	
	/**
	 * It is a Spring object that provides convenient methods to query a database
	 * using JDBC. It uses the previously defined DataSource bean (above). We will use the JdbcTemplate bean
	 * from our DAO classes
	 * 
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	/**
	 * Reverting incomplete database modifications using transactions
	 * Some database modifications involve several SQL queries, for example, inserting an object with
	 * attributes spread across several tables. If one of the queries fails, we would want to undo any
	 * previous ones that were successful
	 * 
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

}