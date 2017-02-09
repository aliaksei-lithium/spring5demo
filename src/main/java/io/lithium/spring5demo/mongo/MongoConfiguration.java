package io.lithium.spring5demo.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;

@Configuration
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

    private final MongoProperties mongoProperties;

    public MongoConfiguration() {
        mongoProperties = null;
    }

    @Autowired
    public MongoConfiguration(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(String.format("mongodb://%s:%d", mongoProperties.getHost(), mongoProperties.getPort()));
    }

    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    }

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }
}
