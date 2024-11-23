package de.thi.cnd.review.ports.outgoing;

import de.thi.cnd.review.domain.model.Review;

public interface ReviewEvents {

    void reviewCreated(Review review);

}
