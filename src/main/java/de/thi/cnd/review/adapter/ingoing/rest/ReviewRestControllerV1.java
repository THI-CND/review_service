package de.thi.cnd.review.adapter.ingoing.rest;

import de.thi.cnd.review.adapter.ingoing.rest.dto.CreateReviewRequestDTO;
import de.thi.cnd.review.adapter.ingoing.rest.dto.ReviewResponseDTO;
import de.thi.cnd.review.adapter.ingoing.rest.dto.UpdateReviewRequestDTO;
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
public class ReviewRestControllerV1 {

    private final ReviewService reviewService;

    public ReviewRestControllerV1(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponseDTO createReview(@RequestBody CreateReviewRequestDTO request) {
        Review review = reviewService.createReview(request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        return new ReviewResponseDTO(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    //TODO: Logik in Service schicht

    @GetMapping
    public List<ReviewResponseDTO> getReviews(@RequestParam(value = "recipeId", required = false) String recipeId) {
        if (recipeId != null) {
            List<Review> list = reviewService.getReviewsByRecipeId(recipeId);
            List<ReviewResponseDTO> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponseDTO(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        } else {
            List<Review> list = reviewService.getReviews(null);
            List<ReviewResponseDTO> reviews = new ArrayList<>();

            for (Review r : list) {
                reviews.add(new ReviewResponseDTO(r.getId(), r.getRecipeId(), r.getAuthor(), r.getRating(), r.getComment()));
            }
            return reviews;
        }
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getReviewById(@PathVariable Long id) {
        Optional<Review> optionalReview = reviewService.getReviewById(id);
        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found");
        }
        Review review = optionalReview.get();
        return new ReviewResponseDTO(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id, @RequestBody UpdateReviewRequestDTO request) {
        Optional<Review> optionalReview = reviewService.updateReview(id, request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        if (optionalReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id " + id + " not found");
        }
        Review review = optionalReview.get();
        return new ReviewResponseDTO(review.getId(), review.getRecipeId(), review.getAuthor(), review.getRating(), review.getComment());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

}
