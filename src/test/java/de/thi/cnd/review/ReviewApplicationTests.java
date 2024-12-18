package de.thi.cnd.review;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thi.cnd.review.adapter.api.rest.dto.CreateReviewRequest;
import de.thi.cnd.review.adapter.api.rest.dto.UpdateReviewRequest;
import de.thi.cnd.review.adapter.jpa.ReviewRepository;
import de.thi.cnd.review.domain.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ReviewControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ReviewRepository reviewRepository;

	@BeforeEach
	public void cleanUp() {
		reviewRepository.deleteAll();
	}

	@Test
	public void testCreateReview() throws Exception {
		CreateReviewRequest review = new CreateReviewRequest();
		review.setRecipeId(1L);
		review.setAuthor("Max Mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");
		mvc.perform(post("/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipeId").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Max Mustermann"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5.0f))
				.andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Sehr lecker!"));
	}

	@Test
	public void testUpdateReview() throws Exception {
		CreateReviewRequest review = new CreateReviewRequest();
		review.setRecipeId(1L);
		review.setAuthor("Max Mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");
		MvcResult result = mvc.perform(post("/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
		long reviewId = jsonNode.get("id").asLong();

		UpdateReviewRequest updatedReview = new UpdateReviewRequest();
		updatedReview.setRecipeId(1L);
		updatedReview.setAuthor("Max Mustermann");
		updatedReview.setRating(1.0f);
		updatedReview.setComment("Sehr schlecht!");
		mvc.perform(put("/reviews/" + reviewId)
						.content(asJsonString(updatedReview))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipeId").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Max Mustermann"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(1.0f))
				.andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Sehr schlecht!"));
	}

	@Test
	public void testDeleteReview() throws Exception {
		CreateReviewRequest review = new CreateReviewRequest();
		review.setRecipeId(1L);
		review.setAuthor("Max Mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");
		MvcResult result = mvc.perform(post("/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
		long reviewId = jsonNode.get("id").asLong();

		mvc.perform(delete("/reviews/" + reviewId))
				.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
