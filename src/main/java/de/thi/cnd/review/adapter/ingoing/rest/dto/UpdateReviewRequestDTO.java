package de.thi.cnd.review.adapter.ingoing.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateReviewRequestDTO {

    private String recipeId;
    private String author;
    private float rating;
    private String comment;

}
