package com.sellandsign.test.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.sellandsign.test.repository.h2",
		entityManagerFactoryRef = "userEntityManager",
		transactionManagerRef = "userTransactionManager")
public class UserConfig {

	@Bean(name="userDatasource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource usersDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="userEntityManager")
	public LocalContainerEntityManagerFactoryBean userEntityManager(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(usersDataSource()).packages("com.sellandsign.test.model.user","com.sellandsign.test.model.apikey").persistenceUnit("testdb").build();
	}


	@Bean
	public PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer2(@Qualifier("userDatasource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("schema-h2.sql"));;

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}
}
