package com.condigence.repository;


import com.condigence.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {

    Optional<Image> getByImageName(String imageName);

}
