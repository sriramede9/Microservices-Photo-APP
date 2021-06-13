package com.sri.photoapp.ui.controllers.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.lang.String;
import com.sri.photoapp.ui.controllers.model.UserEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByUserId(String userid);
}
