package com.condigence.repository;

import com.condigence.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    Profile findByUserId(String id);


}
