package com.koul.sns.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.koul.sns.exception.SnsApplicationException;
import com.koul.sns.fixture.UserEntityFixture;
import com.koul.sns.model.entity.UserEntity;
import com.koul.sns.repository.UserEntityRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@Test
	void 회원가입_정상동작() {

		String userName = "username";
		String password = "password";

		// 회원가입이 정상적으로 동작하는 Mocking
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
		when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

		Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
	}

	@Test
	void 회원가입_중복_유저() {

		String userName = "username";
		String password = "password";

		UserEntity fixture = UserEntityFixture.get(userName, password);

		// 회원가입이 정상적으로 동작하는 Mocking
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
		when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

		Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));
	}

	@Test
	void 로그인_정상동작() {

		String userName = "username";
		String password = "password";

		UserEntity fixture = UserEntityFixture.get(userName, password);
		// Mocking
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
		Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
	}

	@Test
	void 로그인_유저_없는_경우() {

		String userName = "username";
		String password = "password";

		// Mocking
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

		Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
	}

	@Test
	void 로그인_패스워드_틀린_경우() {

		String userName = "username";
		String password = "password";
		String wrongPassword = "wrongPassword";

		UserEntity fixture = UserEntityFixture.get(userName, password);

		// Mocking
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

		Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, wrongPassword));
	}

}
