package de.thi.cnd.review.adapter.outgoing.jpa;

import de.thi.cnd.review.adapter.outgoing.jpa.entities.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaReviewRepository extends CrudRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAll();
    List<ReviewEntity> findByRecipeId(String recipeId);

}
