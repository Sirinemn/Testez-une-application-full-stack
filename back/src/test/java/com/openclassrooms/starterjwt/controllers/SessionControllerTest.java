package com.openclassrooms.starterjwt.controllers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.SessionService;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SessionService service;


	@Autowired
	private ObjectMapper objectMapper;
	
	LocalDateTime rightNow = LocalDateTime.now();
	Date date = new Date();
	
	User initialUser = User.builder()
			.id(1L)
			.email("participant@mail.fr")
			.firstName("participant")
			.lastName("participant")
			.password("participant123")
			.admin(true)
			.createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	List<User> participationList = new ArrayList<User>() {{add(initialUser);}};
	Session initialSession = Session.builder()
			.id(2L)
			.name("test")
			.date(date)
			.description("description test")
			.createdAt(rightNow)
			.teacher(null)
			.updatedAt(rightNow)
			.users(participationList)
			.build();
	@Test
	@WithMockUser(roles = "USER")

	void shouldGetSessionByIdSuccess() throws Exception {
		Long id = initialSession.getId();
		when(service.getById(2L)).thenReturn(initialSession);
		mockMvc.perform(get("/api/session/"+id)).andExpect(status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldFailGetSessionByIdWhenNotFound() throws Exception {
		Long id = initialSession.getId();
		mockMvc.perform(get("/api/session/"+id)).andExpect(status().isNotFound());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldGetAllSession() throws Exception {
		Session session = Session.builder()
				.name("test1")
				.date(date)
				.description("description test1")
				.createdAt(rightNow)
				.teacher(null).updatedAt(rightNow)
				.users(null)
				.build();
		when(service.findAll()).thenReturn(Stream.of(session, initialSession).collect(Collectors.toList()));
		mockMvc.perform(get("/api/session/"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("test"))
		.andExpect(status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldCreateSessionTest() throws Exception {
		SessionDto sessionDto = SessionDto.builder()
				.name("test")
				.date(date)
				.description("description test")
				.createdAt(rightNow)
				.teacher_id(1L)
				.updatedAt(rightNow)
				.users(null)
				.build();
		String content = objectMapper.writeValueAsString(sessionDto);
		when(service.create(initialSession)).thenReturn(initialSession);
		mockMvc.perform(post("/api/session").content(content).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldUpdateSession() throws Exception {
		Long id = initialSession.getId();
		SessionDto updatedContent = SessionDto.builder()
				.name("test")
				.date(date)
				.description("description test")
				.createdAt(rightNow)
				.teacher_id(1L)
				.updatedAt(rightNow)
				.users(null)
				.build();
		String content = objectMapper.writeValueAsString(updatedContent);
		when(service.update(id, initialSession)).thenReturn(initialSession);
		mockMvc.perform(put("/api/session/"+id).accept(MediaType.APPLICATION_JSON).content(content).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldDeleteSessionTest() throws Exception {
		Long id = initialSession.getId();
		when(service.getById(id)).thenReturn(initialSession);
		mockMvc.perform(delete("/api/session/"+id))
		.andExpect(status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldAddParticipation() throws Exception {
		Long id = initialSession.getId();
		User user = User.builder()
				.id(2L)
				.email("test@mail.fr")
				.firstName("firstName")
				.lastName("lastName")
				.password("test123")
				.admin(false)
				.createdAt(rightNow)
				.updatedAt(rightNow)
				.build();
		Long userId = user.getId();
		mockMvc.perform(post("/api/session/"+id+"/participate/"+userId))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldCancelParticipation() throws Exception {
		Long id = initialSession.getId();
		long userId = initialUser.getId();
		mockMvc.perform(delete("/api/session/"+id+"/participate/"+userId))
		.andExpect(status().isOk());
	}	

}
