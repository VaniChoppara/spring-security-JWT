package com.app.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.entity.UserDetailEntity;
import com.app.repository.UserDetailRepository;

@Service
public class UserSecurityService implements UserDetailsService{

	@Autowired
	UserDetailRepository userDetailRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailEntity userDetailEntity = userDetailRepo.findByName(username);
		return new User(userDetailEntity.getName(), userDetailEntity.getPwd(), Collections.emptyList());
	}

	public boolean registerUser(UserDetailEntity user) {
		UserDetailEntity userDetailEntity = userDetailRepo.save(user);
		if(userDetailEntity != null && userDetailEntity.getId() !=null)
			return true;
		return false;
	}
}
