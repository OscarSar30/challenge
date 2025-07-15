package com.test.sp.customer.infrastructure.input.adapter.rest.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScriptExecution implements CommandLineRunner, ApplicationRunner {

    private final DatabaseClient databaseClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        executeScript();
    }

    @Override
    public void run(String... args) throws Exception {
        executeScript();
    }

    private void executeScript() {
        ConnectionFactoryInitializer connectionFactoryInitializer = new ConnectionFactoryInitializer();
        connectionFactoryInitializer.setConnectionFactory(databaseClient.getConnectionFactory());
        connectionFactoryInitializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        connectionFactoryInitializer.afterPropertiesSet();
    }

}
