package com.sri.photoapp.ui.controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sri.photoapp.ui.controllers.model.UserEntity;
import com.sri.photoapp.ui.controllers.model.UserModel;
import com.sri.photoapp.ui.controllers.model.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity createUser(UserEntity userEntity) {

		userEntity.setUserId(UUID.randomUUID().toString());
//		userEntity.setEncryptedPassword(UUID.randomUUID().toString());

		UserEntity save = userRepository.save(userEntity);
		return save;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> findByEmail = userRepository.findByEmail(username);

		if (findByEmail.isPresent()) {
			UserEntity userEntity = findByEmail.get();
			return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
					new ArrayList());

		} else
			throw new UsernameNotFoundException("No User found with give username/email");

	}

	@Override
	public UserEntity getUserDetailsByEmail(String email) {

		Optional<UserEntity> findByEmail = userRepository.findByEmail(email);

		UserEntity userentity = null;

		if (findByEmail.isPresent()) {
			userentity = findByEmail.get();
		}

		return userentity;

	}

	@Override
	public UserEntity getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		Optional<UserEntity> findByUserId = userRepository.findByUserId(userId);
		if(findByUserId.isPresent()) {
			return userRepository.findByUserId(userId).get();
			
		}else throw new RuntimeException("No user found with given id");
	}

}
