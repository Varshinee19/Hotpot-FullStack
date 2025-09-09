package com.hexaware.hotpot.service;
import java.util.List;
import java.util.Optional;

/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Implementation Class.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.CustomerDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerAlreadyExistsException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.DuplicateEmailException;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.OrdersRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {
	@Autowired
	CustomerRepository repo;
	@Autowired
	OrdersRepository orderRepo;
	@Autowired
	CartRepository cartRepo;
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public Customer addCustomer(CustomerDto customer) throws CustomerAlreadyExistsException, DuplicateEmailException {
		Customer cust = readData(customer);
		if (repo.findById(cust.getCustomerId()).isPresent()) {
			throw new CustomerAlreadyExistsException();
		}
		if (repo.findByEmail(cust.getEmail())!= null) {
			throw new DuplicateEmailException();
		}
		log.info("Attempting to add customer with ID: {}", cust.getCustomerId());
		Customer saved=repo.save(cust);
		Cart cart=new Cart();
		cart.setCustomer(saved);
		saved.setCart(cart);
		cartRepo.save(cart);
		return saved;
		
	}

	public Customer updateCustomer(Integer customerId, CustomerDto customer) throws CustomerNotFoundException {
		Customer existing=repo.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
	    if (customer.getName() != null) existing.setName(customer.getName());
	    if (customer.getGender() != null) existing.setGender(customer.getGender());
	    if (customer.getPhone() != null) existing.setPhone(customer.getPhone());
	    if (customer.getAddress() != null) existing.setAddress(customer.getAddress());
		return repo.save(existing);

	}

	@Override
	public String deleteCustomerById(Integer customerId) throws CustomerNotFoundException {
		Optional<Customer> optionalCust = repo.findById(customerId);
		if(!(optionalCust.isPresent())){
			throw new CustomerNotFoundException("Customer with ID not found"+customerId);
		}
		
		repo.deleteById(customerId);
		return "Record deleted successfully";
	}

	public Customer getCustomerByMail(String email) throws CustomerNotFoundException {

		return repo.findByEmail(email);
	}

	@Override
	public Customer getById(int customerId) throws CustomerNotFoundException {

		return repo.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Not Found"));
	}

	public List<Customer> getAllCustomer() {

		return repo.findAll();
	}

	public List<Orders> getAllOrders(int customerId) {

		return orderRepo.findByCustomerCustomerId(customerId);
	}

	public Customer readData(CustomerDto customer) {
		Customer cust = new Customer();
		cust.setName(customer.getName());
		cust.setGender(customer.getGender());
		cust.setEmail(customer.getEmail());
		cust.setAddress(customer.getAddress());
		cust.setPhone(customer.getPhone());
		cust.setRole(customer.getRole());
		return cust;
	}

}
