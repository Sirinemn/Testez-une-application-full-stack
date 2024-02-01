package com.openclassrooms.starterjwt.integration;

import static org.hamcrest.CoreMatchers.containsString;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIT {
	
	@Autowired
	private  MockMvc mockMvc;	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@AfterEach
	void cleanDataBase() {
		teacherRepository.deleteAll();
	}
	@Test
	@WithMockUser(roles = "USER")
	void shouldGetTeacherById() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		Teacher teacher = Teacher.builder().firstName("test").lastName("test").createdAt(rightNow)
				.updatedAt(rightNow).build();
		Long id = teacherRepository.save(teacher).getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/"+id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("test")));
	}

	@Test
	@WithMockUser(roles = "USER")
	void shouldGetAllTeacher() throws Exception {
		LocalDateTime rightNow = LocalDateTime.now();
		Teacher teacher = Teacher.builder().firstName("test").lastName("test").createdAt(rightNow)
				.updatedAt(rightNow).build();
		Teacher teacher1 = Teacher.builder().firstName("test1").lastName("test1").createdAt(rightNow)
				.updatedAt(rightNow).build();
		teacherRepository.save(teacher);
		teacherRepository.save(teacher1);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("test1"));

	}

}


