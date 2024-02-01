//package com.openclassrooms.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
//import com.openclassrooms.starterjwt.controllers.SessionController;
//import com.openclassrooms.starterjwt.mapper.SessionMapper;
//import com.openclassrooms.starterjwt.models.Session;
//import com.openclassrooms.starterjwt.services.SessionService;
//
//@WebMvcTest(controllers = SessionController.class)
//@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)
//@TestPropertySource("classpath:application-test.properties")
//class SessionControllerTest {
//	@MockBean
//    private  SessionMapper sessionMapper;
//	@MockBean
//    private  SessionService sessionService;
//	@Autowired
//	private ObjectMapper objectMapper;
//	@Autowired
//	private MockMvc mockMvc;
//
//
//	@Test
//	void getSessionByIdTest() throws Exception {
//		
//		Date date = new Date();
//		LocalDateTime rightNow = LocalDateTime.now();
//		Session session = Session.builder().id(10L).name("test").date(date)
//				.description("description test").createdAt(rightNow)
//				.teacher(null).updatedAt(rightNow).users(null).build();
//		when(sessionService.getById(10L)).thenReturn(session);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/10")
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
//
//	}
//	@Test
//	void createSessionTest() throws Exception {
//		Date date = new Date();
//		LocalDateTime rightNow = LocalDateTime.now();
//		Session session = Session.builder().id(10L).name("test").date(date)
//				.description("description test").createdAt(rightNow)
//				.teacher(null).updatedAt(rightNow).users(null).build();
//		String content = objectMapper.writeValueAsString(session);
//		when(sessionService.create(session)).thenReturn(session);
//		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/session")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)
//				.content(content);
//		mockMvc.perform(mockRequest)
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
//		
//	}
//	@Test
//	void updateSessionTest() throws Exception {
//		Date date = new Date();
//		LocalDateTime rightNow = LocalDateTime.now();
//		Session updatedSession = Session.builder().id(10L).name("test").date(date)
//				.description("description test").createdAt(rightNow)
//				.teacher(null).updatedAt(rightNow).users(null).build();
//		String updatedContent = objectMapper.writeValueAsString(updatedSession);
//		when(sessionService.update(10L, updatedSession)).thenReturn(updatedSession);
//		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/session/10")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)
//				.content(updatedContent);
//		mockMvc.perform(mockRequest)
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
//
//	}
//
//}
