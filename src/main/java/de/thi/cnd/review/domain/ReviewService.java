package de.thi.cnd.review.domain;

import de.thi.cnd.review.domain.model.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(String recipeId, String author, float rating, String comment);

    List<Review> getReviews();

    List<Review> getReviewsByRecipeId(String recipeId);

    Review getReviewById(Long reviewId);

    Review updateReview(Long reviewId, String recipeId, String author, float rating, String comment);

    void deleteReview(Long reviewId);


}
