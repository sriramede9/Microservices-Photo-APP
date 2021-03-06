package com.sri.photoapp.ui.controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;
import com.sri.photoapp.feignproxy.FeignProxy;
import com.sri.photoapp.ui.controllers.model.UserEntity;
import com.sri.photoapp.ui.controllers.model.UserModel;
import com.sri.photoapp.ui.model.AlbumResponseModel;
import com.sri.photoapp.ui.model.CreateUserResponseModel;
import com.sri.photoapp.ui.model.UserResponseModel;

import feign.FeignException;
import net.bytebuddy.implementation.bind.ParameterLengthResolver;

@RestController
@RequestMapping("/users")
public class UsersController {

	private static final Logger log = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FeignProxy feignProxy;

	@GetMapping("/status/check")
	String status() {

		log.info("Coming from api users");

		return "Working from  ! " + Integer.parseInt(env.getProperty("local.server.port")) + " "
				+ env.getProperty("test.flag");
	}

	@PostMapping()
	ResponseEntity createUser(@Valid @RequestBody UserModel user) {

		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setEmail(user.getEmail());
		// encode
		userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

		UserEntity createUser = userService.createUser(userEntity);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		CreateUserResponseModel createUserResponseModel = modelMapper.map(createUser, CreateUserResponseModel.class);

		return new ResponseEntity<CreateUserResponseModel>(createUserResponseModel, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable("userId") String userId) {

		UserEntity userByUserId = userService.getUserByUserId(userId);

		UserResponseModel returnValue = new ModelMapper().map(userByUserId, UserResponseModel.class);

		String albumsUrl = "http://ALBUMS-WS:/users/%s/albums";

		String format = String.format(albumsUrl, userId);
		AlbumResponseModel[] albums = restTemplate.getForObject(format, AlbumResponseModel[].class);

		returnValue.setAlbums(Arrays.asList(albums));

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

	@GetMapping("/feign/{userId}")
	public ResponseEntity<UserResponseModel> getUserByIdFeign(@PathVariable("userId") String userId) {

		UserEntity userByUserId = userService.getUserByUserId(userId);

		UserResponseModel returnValue = new ModelMapper().map(userByUserId, UserResponseModel.class);

//		String albumsUrl = "http://ALBUMS-WS:/users/%s/albums";
//		
//		String format = String.format(albumsUrl, userId);
//		AlbumResponseModel[] albums = restTemplate.getForObject(format, AlbumResponseModel[].class);
//		
		List<AlbumResponseModel> userAlbums=null;
		try {
			userAlbums = feignProxy.userAlbums(userId);
		} catch (FeignException e) {
			log.error(e.getLocalizedMessage());
		}

		returnValue.setAlbums(userAlbums);

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
}
