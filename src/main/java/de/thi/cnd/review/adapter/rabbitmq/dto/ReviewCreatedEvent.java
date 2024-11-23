package de.thi.cnd.review.adapter.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCreatedEvent {

    private Long id;
    private Long recipeId;
    private String author;
    private float rating;
    private String comment;

}
