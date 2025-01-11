package de.thi.cnd.review.adapter.outgoing.jpa.entities;

import de.thi.cnd.review.domain.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeId;
    private String author;
    private float rating;
    private String comment;

    public ReviewEntity() {
    }

    public Review toReview() {
        return new Review(this.getId(), this.getRecipeId(), this.getAuthor(), this.getRating(), this.getComment());
    }

}
