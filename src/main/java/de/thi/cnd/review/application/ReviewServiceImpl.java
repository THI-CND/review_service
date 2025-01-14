package de.thi.cnd.review.application;

import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.domain.UserService;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewEvents;
import de.thi.cnd.review.ports.outgoing.ReviewRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ReviewEvents reviewEvents;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewEvents reviewEvents, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.reviewEvents = reviewEvents;
        this.userService = userService;
    }

    @Override
    public Review createReview(String recipeId, String author, float rating, String comment) {
        try {
            userService.getUser(author);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new IllegalArgumentException("User not found: " + author);
            } else {
                logger.error("Could not connect to user service: " + e.getMessage());
                throw new IllegalArgumentException("Could not connect to user service: " + e.getMessage());
            }
        }

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
    public List<Review> getReviews(String recipeId) {
        return reviewRepository.getReviews(recipeId);
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
