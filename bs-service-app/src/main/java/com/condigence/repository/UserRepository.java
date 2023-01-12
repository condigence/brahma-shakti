package com.condigence.repository;

import com.condigence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByEmail(String email);

    User findByContact(String contact);

}
