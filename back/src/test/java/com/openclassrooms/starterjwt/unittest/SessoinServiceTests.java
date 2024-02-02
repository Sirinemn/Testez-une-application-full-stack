package com.openclassrooms.starterjwt.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;

@ExtendWith(MockitoExtension.class)
 class SessoinServiceTests {
	
	@InjectMocks
	private SessionService sessionServiceMock;
	@Mock
	private SessionRepository sessionRepository;
	@Mock
	private UserRepository userRepository;
	
	@Test
	void shouldCreateSessionTest() {
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
	void shouldDeleteSessionTest() {
        doNothing().when(sessionRepository).deleteById(1L);
		sessionServiceMock.delete(1L);
        assertAll(() -> sessionServiceMock.delete(1L));
	}
	@Test
	void shouldGetAllSessionTest() {
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
	void shouldGetSessionByIdTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
		Assertions.assertThat(sessionServiceMock.getById(1L)).isNotNull();
	}
	@Test
	void shouldUpdateSessionByIdTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();
		when(sessionRepository.save(session)).thenReturn(session);
        Assertions.assertThat(sessionServiceMock.update(1L, session)).isNotNull();
	}
	@Test
	void shouldAddParticipationToSessionTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		User participant = User.builder().id(1L).email("participant@mail.fr").firstName("participant")
				.lastName("participant").password("participant123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		List<User> participationList = new ArrayList<User>() {{add(participant);}} ;
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(participationList).build();
		User user = User.builder().email("test@mail.fr").firstName("firstName")
				.lastName("lastName").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		Long userId = user.getId();
		Long sessionId = session.getId();

		when(sessionRepository.save(session)).thenReturn(session);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
		
        sessionServiceMock.participate(sessionId, userId);
        assertThat(session.getUsers().size()).isEqualTo(2);
		verify(sessionRepository, times(1)).save(session);

	}
	@Test
	void shouldCancelParticipationToSessionTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		Date date = new Date();
		User participant = User.builder().id(1L).email("participant@mail.fr").firstName("participant")
				.lastName("participant").password("participant123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		List<User> participationList = new ArrayList<User>() {{add(participant);}} ;
		Session session = Session.builder().name("test").date(date)
				.description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(participationList).build();
	
		Long participantId = participant.getId();
		Long sessionId = session.getId();

		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
		
        sessionServiceMock.noLongerParticipate(sessionId, participantId);
        assertThat(session.getUsers().size()).isEqualTo(0);
		verify(sessionRepository, times(1)).save(session);

	}

}
