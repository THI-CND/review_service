package de.thi.cnd.review.adapter.ingoing.grpc;

import de.thi.cnd.review.domain.ReviewService;
import de.thi.cnd.review.ReviewServiceGrpc;
import de.thi.cnd.review.domain.model.Review;
import de.thi.cnd.review.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Optional;

@GrpcService
public class ReviewGrpcController extends ReviewServiceGrpc.ReviewServiceImplBase {

    private final ReviewService reviewService;

    public ReviewGrpcController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void getReviews(Empty request, StreamObserver<ReviewsResponse> responseObserver) {
        List<Review> reviews = reviewService.getReviews();
        ReviewsResponse.Builder responseBuilder = ReviewsResponse.newBuilder();

        for (Review review : reviews) {
            ReviewResponse reviewResponse = ReviewResponse.newBuilder()
                    .setId(review.getId())
                    .setRecipeId(review.getRecipeId())
                    .setAuthor(review.getAuthor())
                    .setRating(review.getRating())
                    .setComment(review.getComment())
                    .build();
            responseBuilder.addReviews(reviewResponse);
        }

        ReviewsResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getReview(ReviewIdRequest request, StreamObserver<ReviewResponse> responseObserver) {
        Optional<Review> optionalReview = reviewService.getReviewById(request.getId());
        if (optionalReview.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Review not found").asException());
            return;
        }

        Review review = optionalReview.get();
        ReviewResponse response = ReviewResponse.newBuilder()
                .setId(review.getId())
                .setRecipeId(review.getRecipeId())
                .setAuthor(review.getAuthor())
                .setRating(review.getRating())
                .setComment(review.getComment())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReview(CreateReviewRequest request, StreamObserver<ReviewResponse> responseObserver) {
        Review review = reviewService.createReview(request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        ReviewResponse response = ReviewResponse.newBuilder()
                .setId(review.getId())
                .setRecipeId(review.getRecipeId())
                .setAuthor(review.getAuthor())
                .setRating(review.getRating())
                .setComment(review.getComment())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateReview(UpdateReviewRequest request, StreamObserver<ReviewResponse> responseObserver) {
        Optional<Review> optionalReview = reviewService.updateReview(request.getId(), request.getRecipeId(), request.getAuthor(), request.getRating(), request.getComment());
        if (optionalReview.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Review not found").asException());
            return;
        }

        Review review = optionalReview.get();
        ReviewResponse response = ReviewResponse.newBuilder()
                .setId(review.getId())
                .setRecipeId(review.getRecipeId())
                .setAuthor(review.getAuthor())
                .setRating(review.getRating())
                .setComment(review.getComment())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReview(ReviewIdRequest request, StreamObserver<Empty> responseObserver) {
        reviewService.deleteReview(request.getId());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
