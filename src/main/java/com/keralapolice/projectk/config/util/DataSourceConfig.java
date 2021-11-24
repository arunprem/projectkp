package com.keralapolice.projectk.config.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	
	
	@Bean
	@Qualifier("kpDataSource")
	@Primary
	@ConfigurationProperties(prefix="kp.datasource")
	DataSource casDataSource() {
		return DataSourceBuilder.create().build();
	}
	

	
	@Bean
	@Qualifier("kpJdbcTemplate")
	JdbcTemplate casJdbcTemplate(@Qualifier("kpDataSource")DataSource kpDataSource) {
		return new JdbcTemplate(kpDataSource);
	}
	

	@Bean
	@Qualifier("kpNamedJdbcTemplate")
	NamedParameterJdbcTemplate casNamedJdbcTemplate(@Qualifier("kpDataSource")DataSource kpNamedJdbcTemplate) {
		return new NamedParameterJdbcTemplate(kpNamedJdbcTemplate);
	}
	

}
