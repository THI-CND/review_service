package de.thi.cnd.review.domain;

import de.thi.cnd.review.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review createReview(String recipeId, String author, float rating, String comment);

    List<Review> getReviews(String recipeId);

    List<Review> getReviewsByRecipeId(String recipeId);

    Optional<Review> getReviewById(Long reviewId);

    Optional<Review> updateReview(Long reviewId, String recipeId, String author, float rating, String comment);

    void deleteReview(Long reviewId);


}
