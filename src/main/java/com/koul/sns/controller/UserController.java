package com.koul.sns.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koul.sns.controller.request.UserJoinRequest;
import com.koul.sns.model.User;
import com.koul.sns.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	// TODO : implements
	@PostMapping("/join")
	public void join(@RequestBody UserJoinRequest request) {
		User user = userService.join(request.getUserName(), request.getPassword());
	}

}
