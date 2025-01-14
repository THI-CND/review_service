package de.thi.cnd.review.adapter.outgoing.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.thi.cnd.review.adapter.outgoing.rabbitmq.dto.ReviewCreatedEvent;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventsImpl implements ReviewEvents {

    private static final Logger logger = LoggerFactory.getLogger(ReviewEventsImpl.class);

    @Value("${app.message.queue.reviews.exchange}")
    private String topicExchange;

    @Value("${app.message.queue.reviews.routing.created}")
    private String routingKeyCreated;

    private final RabbitTemplate rabbitTemplate;

    public ReviewEventsImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void reviewCreated(Review review) {
        ReviewCreatedEvent reviewCreatedEvent = new ReviewCreatedEvent(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
        String content = asJsonString(reviewCreatedEvent);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

        Message message = new Message(content.getBytes(), messageProperties);

        try {
            rabbitTemplate.convertAndSend(topicExchange, routingKeyCreated, message);
        } catch (Exception e) {
            logger.error("Error publishing AMQP message", e);
        }
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
