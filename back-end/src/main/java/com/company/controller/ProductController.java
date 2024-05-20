package com.company.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.model.Product;
import com.company.repository.ProductRepository;
import com.company.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	Flux<Product> getProducts() {

		return productService.getProducts();
	}

	@PostMapping("/products")
	Mono<Product> postProducts(@Valid @RequestBody Product product) {

		return productService.createProduct(product);
	}

	@GetMapping("/products/{id}")
	public Mono<Product> getProductById(@PathVariable(value = "id") Long productId) {
		return productService.getProduct(productId);
	}

	@PutMapping("/products/")
	public Mono<Product> updateProduct(@Valid @RequestBody Product product) {
		return productService.updateProduct(product);
	}

	@DeleteMapping("/products/{id}")
	public Mono<Void> deleteProduct(@PathVariable(value = "id") Long productId) {

		return productService.deleteProduct(productId);
	}

	// Products are Sent to the client as Server Sent Events
	@GetMapping(value = "/stream/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> streamAllProducts() {
		return productService.getProducts().delayElements(Duration.ofSeconds(1));
	}

}
