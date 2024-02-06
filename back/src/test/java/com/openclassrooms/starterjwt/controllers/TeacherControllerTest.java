package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {
	
	@Autowired
	private  MockMvc mockMvc;
	@MockBean
	private TeacherService service;
	
	
	LocalDateTime rightNow = LocalDateTime.now();
	final Teacher initialTeacher = Teacher.builder()
			.id(1L)
			.firstName("test")
			.lastName("test")
			.createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	@Test
	@WithMockUser(roles = "USER")
	public void shouldGetTeacherByIdSuccessTest() throws Exception {
	    Long id = initialTeacher.getId();
		when(service.findById(id)).thenReturn(initialTeacher);
		mockMvc.perform(get("/api/teacher/"+id))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test"));

	}
	@Test
	@WithMockUser(roles = "USER")
	public void shouldFailGetTeacherByIdWhenNotFoundTest() throws Exception {
	    Long id = initialTeacher.getId();
		when(service.findById(id)).thenReturn(initialTeacher);
		mockMvc.perform(get("/api/teacher/"+3))
				.andExpect(status().isNotFound());

	}
	@Test
	@WithMockUser(roles = "USER")
	public void shouldGetAllTeachersSuccessTest() throws Exception {
		Teacher teacher = Teacher.builder().id(2L).firstName("test1").lastName("test1").createdAt(rightNow)
				.updatedAt(rightNow).build();		
		when(service.findAll()).thenReturn(Stream.of(teacher, initialTeacher).collect(Collectors.toList()));
		mockMvc.perform(get("/api/teacher/"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("test1"));

	}

}
