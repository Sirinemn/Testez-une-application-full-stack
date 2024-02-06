package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserRepository userRepository;
	
    ObjectMapper objectMapper = new ObjectMapper();
    
    LocalDateTime rightNow = LocalDateTime.now();
	User initialUser = User.builder()
			.id(1L)
			.email("test@mail.fr")
			.firstName("test")
			.lastName("test")
			.password(passwordEncoder().encode("test123"))
			.admin(true).createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
    
    public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

    @Test
    void shouldLoginUserSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(initialUser.getEmail());
		loginRequest.setPassword("test123");
		String content = objectMapper.writeValueAsString(loginRequest);
		when(userRepository.findByEmail(initialUser.getEmail())).thenReturn(Optional.of(initialUser));
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    void shouldFailLoginUserWhenWrongPassword() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(initialUser.getEmail());
		loginRequest.setPassword("test1234");
		String content = objectMapper.writeValueAsString(loginRequest);
		when(userRepository.findByEmail(initialUser.getEmail())).thenReturn(Optional.of(initialUser));
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(mockRequest)
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }
	@Test
	void shouldRegisterSuccessful() throws Exception {
		
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("newaccount@mail.fr");
		signupRequest.setPassword("test123");
		signupRequest.setFirstName("test");
		signupRequest.setLastName("test");
		when(userRepository.existsByEmail("newaccount@mail.fr")).thenReturn(false);
		String content = objectMapper.writeValueAsString(signupRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	void shouldNotRegisterWhenUserAlreadyExiste() throws Exception {
		
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("newaccount@mail.fr");
		signupRequest.setPassword("test123");
		signupRequest.setFirstName("test");
		signupRequest.setLastName("test");
		when(userRepository.existsByEmail("newaccount@mail.fr")).thenReturn(true);
		String content = objectMapper.writeValueAsString(signupRequest);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
