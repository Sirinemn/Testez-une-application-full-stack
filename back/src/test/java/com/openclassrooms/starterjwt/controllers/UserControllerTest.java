package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends WebSecurityConfigurerAdapter{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService service;
	
	@Autowired
    private  UserMapper userMapper;

	
    ObjectMapper objectMapper = new ObjectMapper();
    User user = User.builder()
    		.id(2L)
    		.email("test@mail.fr")
    		.firstName("test")
    		.lastName("test")
			.password("test123")
			.admin(true)
			.createdAt(null)
			.updatedAt(null)
			.build();
    		

	@Test
	@WithMockUser(roles = "USER")
	public void shouldGetUserByIdSuccessTest() throws Exception {
	    Long id = user.getId();
		when(service.findById(2L)).thenReturn(user);
		UserDto userDto = userMapper.toDto(user);
		String jsonResponse = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(get("/api/user/"+id))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonResponse, true));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void shouldFailGetUserByIdWhenNotFoundTest() throws Exception {
		mockMvc.perform(get("/api/user/"+10))
				.andExpect(status().isNotFound());
	}
	@Test
	public void shouldDeleteUserByIdSuccessTest() throws Exception {
	    Long id = user.getId();
		when(service.findById(2L)).thenReturn(user);
		mockMvc.perform(delete("/api/user/"+id).with(user("test@mail.fr")))
				.andExpect(status().isOk());
	}
	@Test
	public void shouldFailDeleteUserByIdWhenNotAuthoriszTest() throws Exception {
	    Long id = user.getId();
		when(service.findById(2L)).thenReturn(user);
		mockMvc.perform(delete("/api/user/"+id))
				.andExpect(status().isUnauthorized());
	}
}