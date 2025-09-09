package com.hexaware.hotpot.controller;
/*
 * 
 * auth: Varshinee
 * controller class .
 */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hotpot.dto.CustomerDto;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerAlreadyExistsException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.DuplicateEmailException;
import com.hexaware.hotpot.service.ICustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
	private static final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
	@Autowired
	ICustomerService service;
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@PostMapping("/add")
	public Customer addCustomer(@Valid @RequestBody CustomerDto dto) throws CustomerAlreadyExistsException, DuplicateEmailException {
		
		return service.addCustomer(dto);
		
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@PatchMapping("/update/{customerId}")
	public Customer updateCustomer(@Valid @PathVariable Integer customerId,@RequestBody CustomerDto dto) throws CustomerNotFoundException {
		log.info("customer details have been updated");
		return service.updateCustomer(customerId,dto);
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@DeleteMapping("/delete/{customerId}")
	public String deleteCustomer(@PathVariable Integer customerId) throws CustomerNotFoundException {
		return service.deleteCustomerById(customerId);
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/getbyemail/{email}")
	public Customer getByEmail(@PathVariable String email) throws CustomerNotFoundException {
		return service.getCustomerByMail(email);
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/getbyid/{customerId}")
	public Customer getById(@PathVariable Integer customerId) throws CustomerNotFoundException {
		return service.getById(customerId);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get")
	public List<Customer> getAll(){
		return service.getAllCustomer();
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/getorders/{customerId}")
	public List<Orders> getOrders(@PathVariable Integer customerId ){
		return service.getAllOrders(customerId);
	}
	
	

}
