package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;

@ExtendWith(MockitoExtension.class)
public class SessoinServiceTests {
	
	@InjectMocks
	private SessionService sessionServiceMock;
	@Mock
	private SessionRepository sessionRepository;
	@Mock
	private UserRepository userRepository;
	
	@Test
	void CreateSessionTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.save(session)).thenReturn(session);
		sessionServiceMock.create(session);
		verify(sessionRepository, times(1)).save(session);
	}
	
	@Test
	void DeleteSessionTest() {
        doNothing().when(sessionRepository).deleteById(1L);
		sessionServiceMock.delete(1L);
        assertAll(() -> sessionServiceMock.delete(1L));
	}
	@Test
	void GetAllSessionTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		Session session1 = Session.builder().name("test1").date(date)
				.description("description test1").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.findAll()).thenReturn(Stream.of(session, session1).collect(Collectors.toList()));
		Assertions.assertThat(sessionServiceMock.findAll().size()).isEqualTo(2);

	}
	@Test
	void GetSessionByIdTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
		Assertions.assertThat(sessionServiceMock.getById(1L)).isNotNull();
	}
	@Test
	void UpdateSessionByIdTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.save(session)).thenReturn(session);
        Assertions.assertThat(sessionServiceMock.update(1L, session)).isNotNull();
	}

}
