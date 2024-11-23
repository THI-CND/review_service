package de.thi.cnd.review.domain;

import de.thi.cnd.review.domain.model.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(Long recipeId, String author, float rating, String comment);

    List<Review> getReviews();

    List<Review> getReviewsByRecipeId(Long recipeId);

    Review getReviewById(Long reviewId);

    Review updateReview(Long reviewId, Long recipeId, String author, float rating, String comment);

    void deleteReview(Long reviewId);


}
