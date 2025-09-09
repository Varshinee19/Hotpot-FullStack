package com.hexaware.hotpot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hexaware.hotpot.dto.CartDto;

import jakarta.transaction.Transactional;
@Transactional
class CartServiceImplTest {
	@Autowired
	RestTemplate rest;

	@Test
	void testAddCart() {
		CartDto dto=new CartDto();
		dto.setCustomerId(101);
		ResponseEntity<CartDto>response=rest.postForEntity("http//localhost:8080/api/cart/add",dto ,CartDto.class);
		Assertions.assertNotNull(response.getBody());
	}
	@Test
	void testGetById() {
		ResponseEntity<CartDto>response=rest.getForEntity("http//localhost:8080/api/cart/getbyid/2" ,CartDto.class);
		Assertions.assertNotNull(response.getBody());
		
	}
	@Test
	void deleteCart() {
		rest.delete("http//localhost:8080/api/cart/delete/2");
		ResponseEntity<CartDto>response=rest.getForEntity("http//localhost:8080/api/cart/getbyid/2" ,CartDto.class);
		Assertions.assertNull(response.getBody());
		
		
	}

}
