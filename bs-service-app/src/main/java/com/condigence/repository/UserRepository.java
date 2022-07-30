package com.condigence.repository;
import com.condigence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByFirstName(String firstName);

    List<User> findByFirstNameLike(String firstName);

    List<User> findByEmail(String email);


    List<User> findByContact(String contact);

    List<User> findByAddress(String address);

    User findByUsername(String username);
}
