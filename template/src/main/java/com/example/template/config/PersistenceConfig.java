package com.example.template.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.template.model.entities")
public class PersistenceConfig {
}
