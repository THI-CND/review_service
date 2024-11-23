package de.thi.cnd.review.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Review {

    @Setter(AccessLevel.NONE)
    private Long id;
    private Long recipeId;
    private String author;
    private float rating;
    private String comment;

    public Review() {
    }

}
