package com.pozpl.nerannotator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = {"com.pozpl.nerannotator.ner.impl.dao", "com.pozpl.nerannotator.user.impl.dao"})
@PropertySource("application-test.properties")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {
		"com.pozpl.nerannotator.ner",
		"com.pozpl.nerannotator.user",
		"com.pozpl.nerannotator.shared"
})
public class NerAnnotatorApplicationTests {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));

		return dataSource;
	}

}

