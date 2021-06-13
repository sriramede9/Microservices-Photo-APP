package com.sri.photoapp.ui.controllers;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.photoapp.ui.controllers.model.UserEntity;
import com.sri.photoapp.ui.controllers.model.UserModel;

public interface UserService extends UserDetailsService {

	UserEntity createUser(UserEntity userEntity);

	UserEntity getUserDetailsByEmail(String email);

	UserEntity getUserByUserId(String userId);
}
