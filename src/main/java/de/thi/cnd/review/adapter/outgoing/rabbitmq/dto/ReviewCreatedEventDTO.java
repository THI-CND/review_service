package de.thi.cnd.review.adapter.outgoing.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCreatedEventDTO {

    private Long id;
    private String recipeId;
    private String author;
    private float rating;
    private String comment;

}
