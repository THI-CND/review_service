package de.thi.cnd.review.adapter.ingoing.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long id;
    private String recipeId;
    private String author;
    private float rating;
    private String comment;

}
