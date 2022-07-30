package com.condigence.repository;

import com.condigence.model.SuperHero;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SuperHeroRepository extends MongoRepository<SuperHero, String> {

}
