package de.thi.cnd.review.adapter.outgoing.jpa;

import de.thi.cnd.review.adapter.outgoing.jpa.entities.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByRecipeId(String recipeId);

}
