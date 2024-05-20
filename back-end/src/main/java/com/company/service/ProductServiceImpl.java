package com.company.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.model.Product;
import com.company.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	NextSequenceService nextSequenceService;

	public Flux<Product> getProducts() {
		return productRepository.findAll();
	}

	public Mono<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}

	public Mono<Product> createProduct(Product product) {
		product.setId(nextSequenceService.getNextSequence("customSequences"));
		return productRepository.save(product);

	}

	public Mono<Product> updateProduct(final Product product) {

		return productRepository.findById(product.getId()).flatMap(existingProduct -> {
			existingProduct.setProductName(product.getProductName());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setProductDesc(product.getProductDesc());
			return productRepository.save(existingProduct);
		});
	}

	public Mono<Void> deleteProduct(Long id) {
		return productRepository.deleteById(id);

	}

}
