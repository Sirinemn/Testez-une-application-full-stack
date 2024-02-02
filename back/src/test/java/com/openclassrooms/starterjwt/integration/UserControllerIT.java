package com.openclassrooms.starterjwt.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
	
	@Autowired
	private  MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	
	@AfterEach
	void cleanDataBase() {
		userRepository.deleteAll();
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void shouldGetUserById() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().email("test@mail.fr").firstName("test").lastName("test").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		Long id = userRepository.save(user).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(containsString("test")));
	}
	@Test
	void shouldGetUserByIdWhenNotAuthorize() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().email("test@mail.fr").firstName("test").lastName("test").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		Long id = userRepository.save(user).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+id))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());

	}
	@Test
	@WithMockUser(roles = "USER",authorities = {
    "ADMIN" })
	void shouldDeleteUser() throws Exception {
		
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().email("test@mail.fr").firstName("test").lastName("test").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		Long id = userRepository.save(user).getId();
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/"+id)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
