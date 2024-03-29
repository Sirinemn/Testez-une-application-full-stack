package com.openclassrooms.starterjwt.unittest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;

@ExtendWith(MockitoExtension.class)
 class TeacherServiceTests {

	@InjectMocks
	private TeacherService service;
	@Mock
	private TeacherRepository repository;
	
	LocalDateTime rightNow = LocalDateTime.now();
	Teacher initialTeacher = Teacher.builder()
			.id(10L)
			.firstName("test")
			.lastName("test")
			.createdAt(rightNow)
			.updatedAt(rightNow)
			.build();

	@Test
	 void shouldGetAllTeachersTest() {
		
		Teacher teacher = Teacher.builder()
				.id(11L)
				.firstName("test1")
				.lastName("test1")
				.createdAt(rightNow)
				.updatedAt(rightNow)
				.build();
		when(repository.findAll()).thenReturn(Stream.of(initialTeacher, teacher).collect(Collectors.toList()));
		Assertions.assertThat(service.findAll().size()).isEqualTo(2);
		verify(repository, times(1)).findAll();
	}

	@Test
	 void shouldGetTeacherByIdTest() {
	
		when(repository.findById(10L)).thenReturn(Optional.of(initialTeacher));
		Assertions.assertThat(service.findById(10L)).isNotNull();
		verify(repository).findById(10L);
	}

}
