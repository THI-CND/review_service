package de.thi.cnd.review.adapter.outgoing.jpa;

import de.thi.cnd.review.adapter.outgoing.jpa.entities.ReviewEntity;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.ports.outgoing.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JpaReviewRepository jpaReviewRepository;

    public ReviewRepositoryImpl(JpaReviewRepository jpaReviewRepository) {
        this.jpaReviewRepository = jpaReviewRepository;
    }

    @Override
    public Review createReview(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRecipeId(review.getRecipeId());
        reviewEntity.setAuthor(review.getAuthor());
        reviewEntity.setRating(review.getRating());
        reviewEntity.setComment(review.getComment());

        jpaReviewRepository.save(reviewEntity);

        return reviewEntity.toReview();
    }

    @Override
    public List<Review> getReviews(String recipeId) {
        List<ReviewEntity> reviewEntities;
        List<Review> reviews = new ArrayList<>();
        if(recipeId == null) {
            reviewEntities = jpaReviewRepository.findAll();
            reviewEntities.forEach(el -> reviews.add(el.toReview()));
        } else {
            reviewEntities = jpaReviewRepository.findByRecipeId(recipeId);
            reviewEntities.forEach(el -> reviews.add(el.toReview()));
        }
        return reviews;
    }

    @Override
    public Optional<Review>  getReviewById(Long reviewId) {
        Optional<ReviewEntity> reviewEntity = jpaReviewRepository.findById(reviewId);
        return reviewEntity.map(ReviewEntity::toReview);
    }

    @Override
    public List<Review> getReviewsByRecipeId(String recipeId) {
        List<ReviewEntity> reviewEntities = jpaReviewRepository.findByRecipeId(recipeId);
        List<Review> reviews = new ArrayList<>();
        reviewEntities.forEach(el -> reviews.add(el.toReview()));
        return reviews;
    }

    @Override
    public Optional<Review> updateReview(Long reviewId, String recipeId, String author, float rating, String comment) {
        Optional<ReviewEntity> reviewEntity = jpaReviewRepository.findById(reviewId);
        if (reviewEntity.isEmpty()) {
            return Optional.empty();
        }
        ReviewEntity r = reviewEntity.get();
        r.setRecipeId(recipeId);
        r.setAuthor(author);
        r.setRating(rating);
        r.setComment(comment);
        jpaReviewRepository.save(r);

        return Optional.of(r.toReview());
    }

    @Override
    public void deleteReview(Long reviewId) {
        jpaReviewRepository.deleteById(reviewId);
    }
}
