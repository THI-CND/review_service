package de.thi.cnd.review.adapter.api.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateReviewRequest {

    private Long recipeId;
    private String author;
    private float rating;
    private String comment;

}
