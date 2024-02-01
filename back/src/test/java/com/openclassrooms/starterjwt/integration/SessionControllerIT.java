package com.openclassrooms.starterjwt.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIT {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	void cleanDataBase() {
		sessionRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldGetSessionById() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date).description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		Long id = sessionRepository.save(session).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/" + id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("test")));
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldGetAllSession() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date).description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		Session session1 = Session.builder().name("test1").date(date).description("description test1")
				.createdAt(rightNow).teacher(null).updatedAt(rightNow).users(null).build();
		sessionRepository.save(session);
		sessionRepository.save(session1);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("test1"));

	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldCreateSessionTest() throws Exception {
		Date date = new Date();
		LocalDateTime rightNow = LocalDateTime.now();
		SessionDto sessionDto = SessionDto.builder().name("test").date(date).description("description test")
				.createdAt(rightNow).teacher_id(1L).updatedAt(rightNow).users(null).build();
		String content = objectMapper.writeValueAsString(sessionDto);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/session")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldUpdateSessionTest() throws Exception {
		Date date = new Date();
		LocalDateTime rightNow = LocalDateTime.now();
		Session savedSession = Session.builder().name("test").date(date).description("description test")
				.createdAt(rightNow).teacher(null).updatedAt(rightNow).users(null).build();
		Long id= (sessionRepository.save(savedSession)).getId();
		SessionDto updatedContent = SessionDto.builder().name("test").date(date).description("description test")
				.createdAt(rightNow).teacher_id(1L).updatedAt(rightNow).users(null).build();
		String content = objectMapper.writeValueAsString(updatedContent);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/session/"+id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(status().isOk());
	}
}
