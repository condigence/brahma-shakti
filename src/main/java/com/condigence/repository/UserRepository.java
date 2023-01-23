package com.condigence.repository;

import com.condigence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByEmail(String email);

    Optional<User> findByContact(String contact);

    Optional<User> findByUsername(String username);

}
