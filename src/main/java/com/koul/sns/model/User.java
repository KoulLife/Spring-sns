package com.koul.sns.model;

import java.sql.Timestamp;

import com.koul.sns.model.entity.UserEntity;
import com.koul.sns.model.entity.UserRole;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {

	private Integer id;
	private String userName;
	private String password;
	private UserRole userRole;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static User fromEntity(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getUserName(),
			entity.getPassword(),
			entity.getRole(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}
}
