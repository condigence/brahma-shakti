package com.condigence.nsproductservice.repository;


import com.condigence.nsproductservice.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, Long> {
}
