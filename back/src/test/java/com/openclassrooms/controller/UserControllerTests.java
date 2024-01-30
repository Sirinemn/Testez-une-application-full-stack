package com.openclassrooms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
=======
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
>>>>>>> fix/session-cypress-testing
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)
<<<<<<< HEAD
@TestPropertySource("classpath:application-test.properties")
=======
>>>>>>> fix/session-cypress-testing
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService service;


	@Test
	public void getUserControllerTest() throws Exception {
<<<<<<< HEAD
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
=======
	    ObjectMapper objectMapper = new ObjectMapper();
	    		UserDto userDto = UserDto.builder().id(2L).email("test@mail.fr").firstName("test").lastName("test")
				.password("test123").admin(true).createdAt(null).updatedAt(null).build();
	    		
		String jsonResponse = objectMapper.writeValueAsString(userDto);
		mockMvc.perform(get("/api/user/2").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
				.andExpect(content().json(jsonResponse, true));
	}

}
>>>>>>> fix/session-cypress-testing
