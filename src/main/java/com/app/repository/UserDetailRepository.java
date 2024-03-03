package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.UserDetailEntity;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer> {
	
	UserDetailEntity findByName(String name);

}
