package com.openclassrooms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)

public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService service;
	@MockBean
	private UserMapper userMapper;


	@Test
	public void getUserControllerTest() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().id(10L).email("test@mail.fr").firstName("test").lastName("test").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		UserDto userDto = userMapper.toDto(user);
		Long id =userDto.getId();
		when(service.findById(id)).thenReturn(user);
		String jsonResponse = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(get("/api/user/"+id).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse, true));
	}

}

