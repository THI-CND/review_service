package de.thi.cnd.review.application;

import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewEvents;
import de.thi.cnd.review.ports.outgoing.ReviewOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewOutputPort reviews;

    @Autowired
    private ReviewEvents events;

    @Override
    public Review createReview(String recipeId, String author, float rating, String comment) {
        Review review = new Review();
        review.setRecipeId(recipeId);
        review.setAuthor(author);
        review.setRating(rating);
        review.setComment(comment);

        Review savedReview = reviews.createReview(review);
        events.reviewCreated(savedReview);

        return savedReview;
    }

    @Override
    public List<Review> getReviews() {
        return reviews.getReviews();
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviews.getReviewById(reviewId);
    }


    @Override
    public List<Review> getReviewsByRecipeId(String recipeId) {
        return reviews.getReviewsByRecipeId(recipeId);
    }

    @Override
    public Review updateReview(Long reviewId, String recipeId, String author, float rating, String comment) {
        return reviews.updateReview(reviewId, recipeId, author, rating, comment);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviews.deleteReview(reviewId);
    }
}
