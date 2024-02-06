package com.openclassrooms.starterjwt.integration;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
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
	
	LocalDateTime rightNow = LocalDateTime.now();
	User initialUser = User.builder()
			.email("test@mail.fr")
			.firstName("test")
			.lastName("test")
			.password(passwordEncoder().encode("test123"))
			.admin(true).createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	
	@AfterEach
	void cleanDataBase() {
		userRepository.deleteAll();
	}
	
	  
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	@Test
    @Transactional
	void shouldLoginSuccessful() throws Exception {
	
		userRepository.save(initialUser);
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(initialUser.getEmail());
		loginRequest.setPassword("test123");
		String content = objectMapper.writeValueAsString(loginRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void loginWithBadPasswordLoginShouldFail() throws Exception {
	
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(initialUser.getEmail());
		loginRequest.setPassword("1234");
		String content = objectMapper.writeValueAsString(loginRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	@Test
	void shouldRegisterSuccessful() throws Exception {
		
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail(initialUser.getEmail());
		signupRequest.setPassword("test123");
		signupRequest.setFirstName("test");
		signupRequest.setLastName("test");
		String content = objectMapper.writeValueAsString(signupRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}


}
