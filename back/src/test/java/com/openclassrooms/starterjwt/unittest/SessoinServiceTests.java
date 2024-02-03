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
	
	LocalDateTime rightNow = LocalDateTime.now();
	Date date = new Date();

	final User initialUser = User.builder()
			.id(1L)
			.email("participant@mail.fr")
			.firstName("participant")
			.lastName("participant")
			.password("participant123")
			.admin(true).createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	List<User> participationList = new ArrayList<User>() {{add(initialUser);}};
	final Session initialSession = Session.builder()
			.id(1L)
			.name("test")
			.date(date)
			.description("description test")
			.createdAt(rightNow)
			.teacher(null)
			.updatedAt(rightNow)
			.users(participationList)
			.build();

	@Test
	void shouldCreateSessionTest() {
	
		when(sessionRepository.save(initialSession)).thenReturn(initialSession);
		sessionServiceMock.create(initialSession);
		verify(sessionRepository, times(1)).save(initialSession);
	}

	@Test
	void shouldDeleteSessionTest() {
		doNothing().when(sessionRepository).deleteById(1L);
		sessionServiceMock.delete(1L);
		assertAll(() -> sessionServiceMock.delete(1L));
	}

	@Test
	void shouldGetAllSessionTest() {
		Session session = Session.builder().name("test").date(date).description("description test").createdAt(rightNow)
				.teacher(null).updatedAt(rightNow).users(null).build();

		when(sessionRepository.findAll()).thenReturn(Stream.of(session, initialSession).collect(Collectors.toList()));
		Assertions.assertThat(sessionServiceMock.findAll().size()).isEqualTo(2);

	}

	@Test
	void shouldGetSessionByIdTest() {
		
		when(sessionRepository.findById(1L)).thenReturn(Optional.of(initialSession));
		Assertions.assertThat(sessionServiceMock.getById(1L)).isNotNull();
	}

	@Test
	void shouldUpdateSessionByIdTest() {
	
		when(sessionRepository.save(initialSession)).thenReturn(initialSession);
        Assertions.assertThat(sessionServiceMock.update(1L, initialSession)).isNotNull();
	}

	@Test
	void shouldAddParticipationToSessionTest() {

		User user = User.builder().email("test@mail.fr").firstName("firstName")
				.lastName("lastName").password("test123")
				.admin(true).createdAt(rightNow).updatedAt(rightNow).build();
		Long userId = user.getId();
		Long sessionId = initialSession.getId();

		when(sessionRepository.save(initialSession)).thenReturn(initialSession);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(initialSession));

		sessionServiceMock.participate(sessionId, userId);
		assertThat(initialSession.getUsers().size()).isEqualTo(2);
		verify(sessionRepository, times(1)).save(initialSession);

	}

	@Test
	void shouldCancelParticipationToSessionTest() {
	

		Long participantId = initialUser.getId();
		Long sessionId = initialSession.getId();

		when(sessionRepository.save(initialSession)).thenReturn(initialSession);
		when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(initialSession));

		sessionServiceMock.noLongerParticipate(sessionId, participantId);
		assertThat(initialSession.getUsers().size()).isEqualTo(0);
		verify(sessionRepository, times(1)).save(initialSession);

	}

}
