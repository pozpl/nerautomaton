package com.pozpl.nerannotator.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories( basePackages = "com.pozpl.nerannotator.persistence.dao")
public class AppConfig {
}
