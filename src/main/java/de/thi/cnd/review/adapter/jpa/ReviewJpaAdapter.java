package de.thi.cnd.review.adapter.jpa;

import de.thi.cnd.review.adapter.jpa.entities.ReviewEntity;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewJpaAdapter implements ReviewOutputPort {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRecipeId(review.getRecipeId());
        reviewEntity.setAuthor(review.getAuthor());
        reviewEntity.setRating(review.getRating());
        reviewEntity.setComment(review.getComment());

        reviewRepository.save(reviewEntity);

        return reviewEntity.toReview();
    }

    @Override
    public List<Review> getReviews() {
        Iterable<ReviewEntity> all = reviewRepository.findAll();
        List<Review> reviews = new ArrayList<>();
        all.forEach(el -> reviews.add(el.toReview()));
        return reviews;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if (reviewEntity.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        return reviewRepository.findById(reviewId).get().toReview();
    }

    @Override
    public List<Review> getReviewsByRecipeId(String recipeId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByRecipeId(recipeId);
        List<Review> reviews = new ArrayList<>();
        reviewEntities.forEach(el -> reviews.add(el.toReview()));
        return reviews;
    }

    @Override
    public Review updateReview(Long reviewId, String recipeId, String author, float rating, String comment) {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if (reviewEntity.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        ReviewEntity r = reviewEntity.get();
        r.setRecipeId(recipeId);
        r.setAuthor(author);
        r.setRating(rating);
        r.setComment(comment);
        reviewRepository.save(r);

        return r.toReview();
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
