package com.openclassrooms.starterjwt.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
	
	LocalDateTime rightNow = LocalDateTime.now();
	User initialUser = User.builder()
			.email("test@mail.fr")
			.firstName("test")
			.lastName("test")
			.password("test123")
			.admin(true)
			.createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	
	@AfterEach
	void cleanDataBase() {
		userRepository.deleteAll();
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void shouldGetUserById() throws Exception {
	
		Long id = userRepository.save(initialUser).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(containsString("test")));
	}
	@Test
	void shouldNotGetUserByIdWhenNotAuthorize() throws Exception {

		Long id = userRepository.save(initialUser).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+id))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());

	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldNotGetUserByIdWhenNotFound() throws Exception {

		Long id = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+id))
		.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	@Test
	void shouldDeleteUser() throws Exception {
		
		Long id = userRepository.save(initialUser).getId();
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/"+id).with(user("test@mail.fr"))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
