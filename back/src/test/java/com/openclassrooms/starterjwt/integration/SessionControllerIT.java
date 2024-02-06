package com.openclassrooms.starterjwt.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIT {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserRepository userRepository;
	
	LocalDateTime rightNow = LocalDateTime.now();
	Date date = new Date();
	
	User initialUser = User.builder()
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
			.name("test")
			.date(date)
			.description("description test")
			.createdAt(rightNow)
			.teacher(null)
			.updatedAt(rightNow)
			.users(participationList)
			.build();
	

	@AfterEach
	void cleanDataBase() {
		sessionRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldGetSessionById() throws Exception {	
		userRepository.save(initialUser);
		Long id = sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/" + id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("test")));
	}
	@Test
	void shouldNotGetSessionByIdWhenNotAuthorize() throws Exception {	
		userRepository.save(initialUser);
		Long id = sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/" + id))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldNotGetSessionByIdWhenNotFound() throws Exception {	
		Long id = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/" + id))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldGetAllSession() throws Exception {
		userRepository.save(initialUser);
		sessionRepository.save(initialSession);
		Session session = Session.builder()
				.name("test1")
				.date(date)
				.description("description test1")
				.createdAt(rightNow)
				.teacher(null).updatedAt(rightNow)
				.users(null)
				.build();
		sessionRepository.save(session);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/session/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("test1"));

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
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/session")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldUpdateSessionTest() throws Exception {
		userRepository.save(initialUser);
		Long id= sessionRepository.save(initialSession).getId();
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
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/session/"+id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void shouldDeleteSessionTest() throws Exception {
		userRepository.save(initialUser);
		Long id= sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldNotDeleteSessionWhenNotFoundTest() throws Exception {
		Long id= 1L;
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/"+id))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldAddParticipationSessionTest() throws Exception {
		userRepository.save(initialUser);
		sessionRepository.save(initialSession);
		User user = User.builder()
				.email("test@mail.fr")
				.firstName("firstName")
				.lastName("lastName")
				.password("test123")
				.admin(false)
				.createdAt(rightNow)
				.updatedAt(rightNow)
				.build();
		Long userId = (userRepository.save(user)).getId();
		Long sessionId= initialSession.getId();
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/session/{sessionId}/participate/{userId}",sessionId,userId))		
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldNotAddParticipationSessionWhenAlreadyParticipate() throws Exception {
		Long userId = (userRepository.save(initialUser)).getId();
		Long sessionId= sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/session/{sessionId}/participate/{userId}",sessionId,userId))		
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldCancelParticipationSessionTest() throws Exception {
		Long participantId = userRepository.save(initialUser).getId();
		Long sessionId= sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/session/{sessionId}/participate/{participantId}",sessionId,participantId))		
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldNotCancelParticipationSessionWhenNotParticipate() throws Exception {
		userRepository.save(initialUser).getId();
		Long sessionId= sessionRepository.save(initialSession).getId();
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/session/{sessionId}/participate/{participantId}",sessionId,1L))		
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
