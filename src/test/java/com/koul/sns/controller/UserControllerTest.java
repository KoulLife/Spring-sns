package com.koul.sns.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koul.sns.controller.request.UserJoinRequest;
import com.koul.sns.controller.request.UserLoginRequest;
import com.koul.sns.exception.SnsApplicationException;
import com.koul.sns.fixture.UserEntityFixture;
import com.koul.sns.model.User;
import com.koul.sns.model.entity.UserEntity;
import com.koul.sns.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test
	void 회원가입() throws Exception{

		String userName = "userName";
		String password = "password";

		// TODO : mocking
		when(userService.join(userName, password)).thenReturn(mock(User.class));

		mockMvc.perform(post("/api/v1/users/join")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
		).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void 회원가입_username_중복() throws Exception {

		String userName = "userName";
		String password = "password";

		when(userService.join(userName, password)).thenThrow(new SnsApplicationException());

		mockMvc.perform(post("/api/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isConflict());
	}

	@Test
	void 로그인() throws Exception{

		String userName = "userName";
		String password = "password";

		// TODO : mocking
		when(userService.login(userName, password)).thenReturn("test_token");

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void 로그인_없는_회원() throws Exception{

		String userName = "userName";
		String password = "password";

		// TODO : mocking
		when(userService.login(userName, password)).thenThrow(new SnsApplicationException());

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	void 로그인_틀린_password() throws Exception{

		String userName = "userName";
		String password = "password";

		// TODO : mocking
		when(userService.login(userName, password)).thenThrow(new SnsApplicationException());

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

}
