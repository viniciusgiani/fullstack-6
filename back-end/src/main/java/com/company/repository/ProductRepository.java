package com.company.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.company.model.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, Long> {
	

}
