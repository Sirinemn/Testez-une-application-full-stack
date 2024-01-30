package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	@Mock
	private UserRepository repository;
	@BeforeEach
	public void init() {
		service = new UserService(repository);
	}
	
	@Test
	public void getUserTest() {
		LocalDateTime rightNow = LocalDateTime.now();
		User user = User.builder().id((long) 10).email("test@mail.fr").firstName("test").lastName("test").password("test123").admin(true).createdAt(rightNow)
				.updatedAt(rightNow).build();
		when(repository.findById((long) 10)).thenReturn(Optional.of(user));
		Assertions.assertThat(service.findById((long) 10)).isNotNull();
		verify(repository).findById(10L);

		}
	@Test
	void DeleteUserTest() {
        doNothing().when(repository).deleteById(1L);
		service.delete(1L);
        assertAll(() -> service.delete(1L));
	}
}
