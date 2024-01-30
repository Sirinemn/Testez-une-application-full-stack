package com.openclassrooms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserMapper userMapper;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService service;


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
