package com.project.journal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//Here due to this on every method annotated with Transactional, it will create transactional context(Container) which will have all DB operations to achieve atomicity
// for every request spring will create different container like for Ram and Shyam to store their data separately -- Isolation achieved

public class TransactionConfig {
    @Bean
    public PlatformTransactionManager addMongoFactory(MongoDatabaseFactory dbFactory) { // To create connection with Mongo we use MongoDBFactory
        return new MongoTransactionManager(dbFactory);
    }
}
//PlatformTransactionManager will manage all transaction it is interface
//MongoTransactionManager : it will implement PlatformaTransactionManager