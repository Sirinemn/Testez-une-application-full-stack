package com.openclassrooms.starterjwt.integration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private WebApplicationContext applicationContext;

	@AfterEach
	void cleanDataBase() {
		userRepository.deleteAll();
	}

	@BeforeEach
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).apply(springSecurity()).build();
	}

	@Test
	void shouldLoginSuccessful() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().email("test@mail.fr").firstName("test").lastName("test").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		userRepository.save(user);
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(user.getEmail());
		loginRequest.setPassword(user.getPassword());
		String content = objectMapper.writeValueAsString(loginRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldLoginFail() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test@mail.fr");
		loginRequest.setPassword("1234");
		String content = objectMapper.writeValueAsString(loginRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

}
