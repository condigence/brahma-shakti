package com.condigence.repository;

import com.condigence.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FavouriteRepository extends MongoRepository<Favourite, String> {
    List<Favourite> findAllByUserId(String userId);

    Favourite save(Favourite favourite);
}
