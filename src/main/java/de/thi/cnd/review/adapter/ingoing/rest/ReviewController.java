package de.thi.cnd.review.adapter.ingoing.rest;

import de.thi.cnd.review.adapter.ingoing.rest.dto.CreateReviewRequest;
import de.thi.cnd.review.adapter.ingoing.rest.dto.ReviewResponse;
import de.thi.cnd.review.adapter.ingoing.rest.dto.UpdateReviewRequest;
import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.domain.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse createReview(@RequestBody CreateReviewRequest request) {
        Review review = reviewService.createReview(request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        return new ReviewResponse(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    //TODO: Logik in Service schicht

    @GetMapping
    public List<ReviewResponse> getReviews(@RequestParam(value = "recipeId", required = false) String recipeId) {
        if (recipeId != null) {
            List<Review> list = reviewService.getReviewsByRecipeId(recipeId);
            List<ReviewResponse> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        } else {
            List<Review> list = reviewService.getReviews(null);
            List<ReviewResponse> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        }
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable Long id) {
        Optional<Review> optionalReview = reviewService.getReviewById(id);
        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found");
        }
        Review review = optionalReview.get();
        return new ReviewResponse(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    @PutMapping("/{id}")
    public ReviewResponse updateReview(@PathVariable Long id, @RequestBody UpdateReviewRequest request) {
        Optional<Review> optionalReview = reviewService.updateReview(id, request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found");
        }
        Review review = optionalReview.get();
        return new ReviewResponse(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

}
