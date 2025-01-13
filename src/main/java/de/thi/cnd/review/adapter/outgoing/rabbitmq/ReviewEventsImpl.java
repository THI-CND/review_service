package de.thi.cnd.review.adapter.outgoing.rabbitmq;

import de.thi.cnd.review.adapter.outgoing.rabbitmq.dto.ReviewCreatedEvent;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventsImpl implements ReviewEvents {
    
    private final MessagingService messagingService;

    public ReviewEventsImpl(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Value("${app.message.queue.reviews.routing.created}")
    private String routingKeyCreated;

    @Override
    public void reviewCreated(Review review) {
        messagingService.publish(routingKeyCreated, new ReviewCreatedEvent(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment()));
    }

}
