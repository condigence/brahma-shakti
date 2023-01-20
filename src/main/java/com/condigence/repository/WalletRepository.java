package com.condigence.repository;

import com.condigence.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {

    Wallet getByUserId(String userId);
}
