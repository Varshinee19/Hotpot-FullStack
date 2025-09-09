package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.CustomerDto;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerAlreadyExistsException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.DuplicateEmailException;

public interface ICustomerService {
	
	public Customer addCustomer(CustomerDto customer)throws CustomerAlreadyExistsException,DuplicateEmailException;
	public Customer updateCustomer(Integer customerId,CustomerDto customer) throws CustomerNotFoundException;
	public Customer getCustomerByMail(String email)throws CustomerNotFoundException;
	public Customer getById(int customerId) throws CustomerNotFoundException;
	public String deleteCustomerById(Integer customerId)throws CustomerNotFoundException;
	public List<Customer> getAllCustomer();
	public List<Orders> getAllOrders(int customerId);
	
	
	

}
