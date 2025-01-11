package de.thi.cnd.review.ports.outgoing;

import de.thi.cnd.review.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewOutputPort {

    Review createReview(Review review);

    List<Review> getReviews();

    List<Review> getReviewsByRecipeId(String recipeId);

    Optional<Review>getReviewById(Long reviewId);

    Optional<Review> updateReview(Long reviewId, String recipeId, String author, float rating, String comment);

    void deleteReview(Long reviewId);

}
