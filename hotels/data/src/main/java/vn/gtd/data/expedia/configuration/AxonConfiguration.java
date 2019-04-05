package vn.gtd.data.expedia.configuration;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.serialization.json.JacksonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class AxonConfiguration {

    /* Mandatory configuration */
    @Bean
    public SpringAMQPMessageSource springAMQPMessageSource(JacksonSerializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {
            final Logger logger = LoggerFactory.getLogger(getClass());
            @Override
            @RabbitListener(queues = "${external.amqp.queue}", exclusive = true)
            public void onMessage(Message message, Channel channel) {
                logger.info("Received message: {}", message);
                logger.info("Channel {}", channel);
                super.onMessage(message, channel);
            }
        };
    }

    /* Mandatory configuration */
    @Autowired
    public void configure(EventHandlingConfiguration configuration, SpringAMQPMessageSource springAMQPMessageSource) {
        configuration.registerSubscribingEventProcessor(
                "continentEvents", c -> springAMQPMessageSource
        );
//        configuration.registerSubscribingEventProcessor(
//                "productEvents", c -> springAMQPMessageSource
//        );
    }

//    @Bean
//    @Scope(value = "prototype")
//    public AMQPMessageConverter amqpMessageConverter(JacksonSerializer jacksonSerializer) {
//        return new DefaultAMQPMessageConverter(jacksonSerializer);
//    }

    @Bean
    @Primary
    public JacksonSerializer jacksonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    @Primary
    public EventHandlingConfiguration configuration() {
        return new EventHandlingConfiguration();
    }


}
