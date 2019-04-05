package vn.gtd.connectors.expedia.configuration;

import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.serialization.upcasting.event.NoOpEventUpcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AxonMongoInfraStructureConfig.class)
public class AxonConfiguration {
    @Bean
    public EventUpcaster eventUpcaster() {
        return NoOpEventUpcaster.INSTANCE;
    }
}
