package vn.gtd.data.expedia.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.boot.AMQPProperties;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.support.ResourceTransactionManager;

@Configuration
public class AMQPConfiguration {
    @Value("${spring.application.index}")
    private Integer index;

    @Value("${external.amqp.queue}")
    private String queueName;

    @Value("${external.amqp.bind.route-key}")
    private String routeKey;

    @Value("${external.amqp.exchange.fanout.auto-delete}")
    private boolean autoDelete;

    private final RabbitProperties rabbitProperties;
    private final AMQPProperties amqpProperties;

    public AMQPConfiguration(RabbitProperties rabbitProperties, AMQPProperties amqpProperties) {
        this.rabbitProperties = rabbitProperties;
        this.amqpProperties = amqpProperties;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, amqpProperties.isDurableMessages());
    }

    @Bean
    Exchange exchange() {
        ExchangeBuilder builder = ExchangeBuilder
                .fanoutExchange(amqpProperties.getExchange())
                .durable(amqpProperties.isDurableMessages());
        if (this.autoDelete) {
            builder.autoDelete();
        }
        return builder.build();
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routeKey)
                .noargs();
    }


    @Bean(name = "rabbitTransactionManager")
    ResourceTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public SpringAMQPMessageSource springAMQPMessageSource(AMQPMessageConverter amqpMessageConverter) {
        return new SpringAMQPMessageSource(amqpMessageConverter) {
            final Logger logger = LoggerFactory.getLogger(getClass());
            @Override
            @RabbitListener(queues = "${external.amqp.queue}", exclusive = true)
            public void onMessage(Message message, Channel channel) {
                logger.info("Received message: {}", message.getBody());
                logger.info("Channel {}", channel);
                super.onMessage(message, channel);
            }
        };
    }

    @Bean
    @Scope(value = "prototype")
    public AMQPMessageConverter amqpMessageConverter(Serializer jacksonSerializer) {
        return new DefaultAMQPMessageConverter(jacksonSerializer);
    }

    @Bean
    @Primary
    public Serializer jacksonSerializer() {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new Jdk8Module());
//        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return new JacksonSerializer(mapper);
    }
}
