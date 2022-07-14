package com.condigence.nsproductservice.repository;


import com.condigence.nsproductservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, Long> {
	
	List<Item> findByBrandId(Long id);



}
