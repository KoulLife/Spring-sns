package com.koul.sns.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.koul.sns.exception.SnsApplicationException;
import com.koul.sns.model.User;
import com.koul.sns.model.entity.UserEntity;
import com.koul.sns.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserEntityRepository userEntityRepository;

	// TODO : IMPLEMENTS
	public User join(String userName, String password) {
		// 중복 userName 체크
		userEntityRepository.findByUserName(userName).ifPresent(it -> {
			throw new SnsApplicationException();
		});

		// user 등록
		UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));

		return User.fromEntity(userEntity);
	}

	// TODO : implement
	public String login(String userName, String password) {
		// 회원가입 여부 체크
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException());

		// 비밀번호 체크
		if (!userEntity.getPassword().equals(password)) {
			throw new SnsApplicationException();
		}

		// TODO : 토큰 생성

		return "";
	}
}
