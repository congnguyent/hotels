package vn.gtd.connectors.expedia.configuration;

import com.mongodb.MongoClient;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.StorageStrategy;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(EventSourcingCollectionProperties.class)
public class AxonMongoInfraStructureConfig {
    private final EventSourcingCollectionProperties eventSourcingCollectionProperties;
    private final MongoProperties mongoProperties;

    public AxonMongoInfraStructureConfig(EventSourcingCollectionProperties eventSourcingCollectionProperties, MongoProperties mongoProperties) {
        this.eventSourcingCollectionProperties = eventSourcingCollectionProperties;
        this.mongoProperties = mongoProperties;
    }

    @Bean(name = "axonMongoTemplate")
    MongoTemplate axonMongoTemplate(MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new DefaultMongoTemplate(mongoClient, mongoProperties.getDatabase())
                .withDomainEventsCollection(eventSourcingCollectionProperties.getEventsCollectionName())
                .withSnapshotCollection(eventSourcingCollectionProperties.getSnapshotCollectionName());

        return mongoTemplate;
    }

    @Bean
    EventStorageEngine eventStorageEngine(
            JacksonSerializer axonJacksonSerializer,
            MongoTemplate axonMongoTemplate,
            EventUpcaster eventUpcaster,
            StorageStrategy storageStrategy
    ) {
        return new MongoEventStorageEngine(
                axonJacksonSerializer,
                eventUpcaster,
                axonMongoTemplate,
                storageStrategy
        );
    }

    @Bean
    StorageStrategy storageStrategy() {
        return new DocumentPerCommitStorageStrategy();
    }

    @Bean(name = "axonJacksonSerializer")
    @Primary
    JacksonSerializer jacksonSerializer() {
        return new JacksonSerializer();
    }

}