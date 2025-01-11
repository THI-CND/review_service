package de.thi.cnd.review.adapter.outgoing.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.message.queue.reviews.name}")
    private String queueName;

    @Value("${app.message.queue.reviews.exchange}")
    private String topicExchangeName;

    @Value("${app.message.queue.reviews.routing.created}")
    private String routingKeyCreated;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Declarables bindings() {
        return new Declarables(
                BindingBuilder.bind(queue()).to(exchange()).with(routingKeyCreated)
        );
    }

}
