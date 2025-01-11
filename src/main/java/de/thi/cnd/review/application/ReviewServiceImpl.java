package de.thi.cnd.review.application;

import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewEvents;
import de.thi.cnd.review.ports.outgoing.ReviewOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewOutputPort reviewRepository;

    @Autowired
    private ReviewEvents reviewEvents;

    @Override
    public Review createReview(String recipeId, String author, float rating, String comment) {
        Review review = new Review();
        review.setRecipeId(recipeId);
        review.setAuthor(author);
        review.setRating(rating);
        review.setComment(comment);

        Review savedReview = reviewRepository.createReview(review);
        reviewEvents.reviewCreated(savedReview);

        return savedReview;
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.getReviews();
    }

    @Override
    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.getReviewById(reviewId);
    }

    @Override
    public List<Review> getReviewsByRecipeId(String recipeId) {
        return reviewRepository.getReviewsByRecipeId(recipeId);
    }

    @Override
    public Optional<Review> updateReview(Long reviewId, String recipeId, String author, float rating, String comment) {
        return reviewRepository.updateReview(reviewId, recipeId, author, rating, comment);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteReview(reviewId);
    }
}
