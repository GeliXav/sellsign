package com.sellandsign.test.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.sellandsign.test.repository.recipient",
		entityManagerFactoryRef = "recipientsEntityManager",
		transactionManagerRef = "recipientsTransactionManager")
public class RecipientsConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.second-datasource")
	public DataSource recipientsDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name="recipientsEntityManager")
	public LocalContainerEntityManagerFactoryBean recipientsEntityManager(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(recipientsDataSource())
				       .packages("com.sellandsign.test.model.recipients")
					   .persistenceUnit("dbchallenge")
				       .build();
	}

	@Primary
	@Bean
	public PlatformTransactionManager recipientsTransactionManager(@Qualifier("recipientsEntityManager") EntityManagerFactory recipientsEntityManager) {
		return new JpaTransactionManager(recipientsEntityManager);
	}
}
