//package com.example.demo;
//import com.example.demo.models.dtos.ExerciseDTO;
//import com.example.demo.models.dtos.UserDTO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class IntegrationTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Test
//	public void testIntegration() throws Exception {
//		// Creare utilizator
//		UserDTO userDTO = new UserDTO();
//		userDTO.setName("test user");
//		userDTO.setPassword("pass");
//
//		userDTO.setExerciseIds(userDTO.getExerciseIds().stream()
//				.filter(Objects::nonNull)
//				.collect(Collectors.toSet()));
//
//		MvcResult userResult = mockMvc.perform(MockMvcRequestBuilders
//						.post("/api/users")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(userDTO)))
//						.andExpect(status().isOk())
//						.andReturn();
//
//		Long userId = objectMapper.readValue(userResult.getResponse().getContentAsString(), UserDTO.class).getId();
//
//		// Creare exercițiu
//		ExerciseDTO exerciseDTO = new ExerciseDTO();
//		exerciseDTO.setName("Push-up");
//		exerciseDTO.setSets(3);
//		exerciseDTO.setReps(10);
//		exerciseDTO.setWeight(0);
//
//
//		MvcResult exerciseResult = mockMvc.perform(MockMvcRequestBuilders
//						.post("/api/exercises")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(exerciseDTO)))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		Long exerciseId = objectMapper.readValue(exerciseResult.getResponse().getContentAsString(), ExerciseDTO.class).getId();
//
//		// Asociere exerciții cu utilizatorul
//		mockMvc.perform(MockMvcRequestBuilders
//						.post("/api/users/{userId}/associate-exercises", userId)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(List.of(exerciseId))))
//				.andExpect(status().isOk());
//
//		// Obținere utilizator și verificare exerciții asociate
//		mockMvc.perform(MockMvcRequestBuilders
//						.get("/api/users/{userId}", userId))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.exercises[0].id").value(exerciseId));
//
//		// Obținere exercițiu și verificare utilizatori asociați
//		mockMvc.perform(MockMvcRequestBuilders
//						.get("/api/exercises/{exerciseId}", exerciseId))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.users[0].id").value(userId));
//	}
//}