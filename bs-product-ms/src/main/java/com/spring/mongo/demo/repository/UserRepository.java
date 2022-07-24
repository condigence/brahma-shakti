package com.spring.mongo.demo.repository;
import com.spring.mongo.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByFirstName(String firstName);

    List<User> findByFirstNameLike(String firstName);

    List<User> findByEmail(String email);


    List<User> findByContact(String contact);

    List<User> findByAddress(String address);
}
