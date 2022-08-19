package com.condigence.repository;

import com.condigence.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Subscription save(Subscription subscription);

    List<Subscription> findByUserId(String userId);
}
