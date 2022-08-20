package com.condigence.repository;
import com.condigence.model.Profile;
import com.condigence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    Profile findByUserId(String userId);

}
