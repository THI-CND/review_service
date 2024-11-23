package de.thi.cnd.review.ports.outgoing;

import de.thi.cnd.review.domain.model.Review;

import java.util.List;

public interface ReviewOutputPort {

    Review createReview(Review review);

    List<Review> getReviews();

    List<Review> getReviewsByRecipeId(Long recipeId);

    Review getReviewById(Long reviewId);

    Review updateReview(Long reviewId, Long recipeId, String author, float rating, String comment);

    void deleteReview(Long reviewId);

}
