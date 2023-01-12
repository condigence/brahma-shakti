package com.condigence.repository;

import com.condigence.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByUserId(String id);

    List<Address> findByContact(String contact);

    Optional<Address> findOneByUserId(String id);

//    @Query("SELECT a FROM Address u WHERE a.isDefault = ?Y")
//    Address findAddressByIsDefault(String isDefault);
}
