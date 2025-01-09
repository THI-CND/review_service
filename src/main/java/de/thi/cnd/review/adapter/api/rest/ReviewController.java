package de.thi.cnd.review.adapter.api.rest;

import de.thi.cnd.review.adapter.api.rest.dto.CreateReviewRequest;
import de.thi.cnd.review.adapter.api.rest.dto.ReviewResponse;
import de.thi.cnd.review.adapter.api.rest.dto.UpdateReviewRequest;
import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.domain.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping
    public ReviewResponse createReview(@RequestBody CreateReviewRequest request) {
        Review r = service.createReview(request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        return new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment());
    }

    @GetMapping
    public List<ReviewResponse> getReviews(@RequestParam(value = "recipeId", required = false) String recipeId) {
        if (recipeId != null) {
            List<Review> list = service.getReviewsByRecipeId(recipeId);
            List<ReviewResponse> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        } else {
            List<Review> list = service.getReviews();
            List<ReviewResponse> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        }
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable Long id) {
        Review r = service.getReviewById(id);
        return new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment());
    }

    @PutMapping("/{id}")
    public ReviewResponse updateReview(@PathVariable Long id, @RequestBody UpdateReviewRequest request) {
        Review r = service.updateReview(id, request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        return new ReviewResponse(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
    }

}
