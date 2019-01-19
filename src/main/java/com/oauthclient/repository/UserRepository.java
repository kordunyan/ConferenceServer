package com.oauthclient.repository;

import com.oauthclient.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
