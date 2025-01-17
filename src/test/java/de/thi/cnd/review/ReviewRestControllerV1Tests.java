package de.thi.cnd.review;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thi.cnd.review.adapter.ingoing.rest.dto.CreateReviewRequestDTO;
import de.thi.cnd.review.adapter.ingoing.rest.dto.UpdateReviewRequestDTO;
import de.thi.cnd.review.adapter.outgoing.jpa.JpaReviewRepository;
import de.thi.cnd.review.domain.UserService;
import de.thi.cnd.review.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReviewRestControllerV1Tests {

	private final MockMvc mvc;
	private final JpaReviewRepository reviewRepository;

	@MockBean
	private UserService userService;

	@Autowired
	public ReviewRestControllerV1Tests(MockMvc mvc, JpaReviewRepository reviewRepository) {
		this.mvc = mvc;
		this.reviewRepository = reviewRepository;
	}

	@BeforeEach
	public void cleanUp() {
		reviewRepository.deleteAll();
	}

	@Test
	void testCreateReview() throws Exception {
		CreateReviewRequestDTO review = new CreateReviewRequestDTO();
		review.setRecipeId("1L");
		review.setAuthor("mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");

		when(userService.getUser("mustermann")).thenReturn(new User("mustermann", "Max", "Mustermann"));

		mvc.perform(post("/api/v1/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipeId").value("1L"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("mustermann"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5.0f))
				.andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Sehr lecker!"));
	}

	@Test
	void testUpdateReview() throws Exception {
		CreateReviewRequestDTO review = new CreateReviewRequestDTO();
		review.setRecipeId("1L");
		review.setAuthor("Max Mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");
		MvcResult result = mvc.perform(post("/api/v1/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
		long reviewId = jsonNode.get("id").asLong();

		UpdateReviewRequestDTO updatedReview = new UpdateReviewRequestDTO();
		updatedReview.setRecipeId("1L");
		updatedReview.setAuthor("Max Mustermann");
		updatedReview.setRating(1.0f);
		updatedReview.setComment("Sehr schlecht!");
		mvc.perform(put("/api/v1/reviews/" + reviewId)
						.content(asJsonString(updatedReview))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recipeId").value("1L"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Max Mustermann"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(1.0f))
				.andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Sehr schlecht!"));
	}

	@Test
	void testDeleteReview() throws Exception {
		CreateReviewRequestDTO review = new CreateReviewRequestDTO();
		review.setRecipeId("1L");
		review.setAuthor("Max Mustermann");
		review.setRating(5.0f);
		review.setComment("Sehr lecker!");
		MvcResult result = mvc.perform(post("/api/v1/reviews")
						.content(asJsonString(review))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
		long reviewId = jsonNode.get("id").asLong();

		mvc.perform(delete("/api/v1/reviews/" + reviewId))
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
