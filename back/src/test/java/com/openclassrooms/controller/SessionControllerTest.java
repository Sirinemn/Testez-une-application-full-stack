package com.openclassrooms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;

@WebMvcTest(controllers = SessionController.class)
@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)
@TestPropertySource("classpath:application-test.properties")
class SessionControllerTest {
	@MockBean
    private  SessionMapper sessionMapper;
	@MockBean
    private  SessionService sessionService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;


	@Test
	void getSessionByIdTest() throws Exception {
		
		Date date = new Date();
		LocalDateTime rightNow = LocalDateTime.now();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		SessionDto sessionDto = sessionMapper.toDto(session);
		String jsonResponse = objectMapper.writeValueAsString(sessionDto);
		mockMvc.perform(get("/api/session"+sessionDto.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
		.andExpect(content().json(jsonResponse, true));

	}

}
