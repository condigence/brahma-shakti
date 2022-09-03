package com.condigence.repository;

import com.condigence.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByUserId(String id);
}
