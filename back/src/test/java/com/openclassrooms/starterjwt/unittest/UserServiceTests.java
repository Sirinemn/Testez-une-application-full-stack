package com.openclassrooms.starterjwt.unittest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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

	LocalDateTime rightNow = LocalDateTime.now();
	User initialUser = User.builder()
			.id(10L)
			.email("test@mail.fr")
			.firstName("test")
			.lastName("test")
			.password("test123")
			.admin(true)
			.createdAt(rightNow)
			.updatedAt(rightNow)
			.build();
	
	@Test
	public void shouldGetUserTest() {
		
		when(repository.findById(10L)).thenReturn(Optional.of(initialUser));
		Assertions.assertThat(service.findById(10L)).isNotNull();
		verify(repository).findById(10L);

		}
	@Test
	void shouldDeleteUserTest() {
        doNothing().when(repository).deleteById(10L);
		service.delete(10L);
        assertAll(() -> service.delete(10L));
	}
}
