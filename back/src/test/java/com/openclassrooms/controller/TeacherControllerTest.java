//package com.openclassrooms.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
//import com.openclassrooms.starterjwt.controllers.TeacherController;
//import com.openclassrooms.starterjwt.dto.TeacherDto;
//import com.openclassrooms.starterjwt.mapper.TeacherMapper;
//import com.openclassrooms.starterjwt.models.Teacher;
//import com.openclassrooms.starterjwt.services.TeacherService;
//
//@WebMvcTest(controllers = TeacherController.class)
//@ContextConfiguration(classes= SpringBootSecurityJwtApplication.class)
//@TestPropertySource("classpath:application-test.properties")
//class TeacherControllerTest {
//	
//	@MockBean
//    private TeacherMapper teacherMapper;
//	@MockBean
//    private TeacherService teacherService;
//	@Autowired
//	private ObjectMapper objectMapper;
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Test
//	void getTeacherByIdTest() throws Exception {
//		LocalDateTime rightNow = LocalDateTime.now();
//		Teacher teacher = Teacher.builder().id(10L).firstName("test").lastName("test").createdAt(rightNow)
//				.updatedAt(rightNow).build();
//		Mockito.when(teacherService.findById(10L)).thenReturn(teacher);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/10")
//		.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
//	}
//	
//	@Test
//	@Disabled
//	void getAllTeachersTest() throws Exception {
//		LocalDateTime rightNow = LocalDateTime.now();
//		Teacher teacher = Teacher.builder().id(10L).firstName("test").lastName("test").createdAt(rightNow)
//				.updatedAt(rightNow).build();
//		Teacher teacher1 = Teacher.builder().id(11L).firstName("test1").lastName("test1").createdAt(rightNow)
//				.updatedAt(rightNow).build();
//	
//		List<TeacherDto> teachersDto = teacherMapper.toDto(Stream.of(teacher, teacher1).collect(Collectors.toList()));
//
//		String jsonResponse = objectMapper.writeValueAsString(teachersDto);
//		mockMvc.perform(get("/api/teacher").contentType(MediaType.APPLICATION_JSON_VALUE))
//		.andExpect(status().isOk())
//		.andExpect(content().json(jsonResponse, true));
//	}
//}
//
//
