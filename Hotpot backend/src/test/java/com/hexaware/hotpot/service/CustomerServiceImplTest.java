package com.hexaware.hotpot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.CustomerDto;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entitiesenum.Role;
import com.hexaware.hotpot.exception.CustomerAlreadyExistsException;
import com.hexaware.hotpot.exception.DuplicateEmailException;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.OrdersRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class CustomerServiceImplTest {
	@Autowired
	ICustomerService customerService;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	OrdersRepository orderRepo;
	private CustomerDto customerDto;
	@BeforeEach
	void setup() {
		orderRepo.deleteAll();
		customerRepo.deleteAll();
        customerDto = new CustomerDto();
        customerDto.setName("Alice");
        customerDto.setEmail("alice123@gmail.com");
        customerDto.setAddress("123 Main Street");
        customerDto.setGender("Female");
        customerDto.setPhone("1234567890");
        customerDto.setRole(Role.customer);
	}
 
    @Test
    void testAddCustomer() throws CustomerAlreadyExistsException, DuplicateEmailException {
        Customer saved = customerService.addCustomer(customerDto);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Alice", saved.getName());
    }


    @Test
    void testUpdateCustomer() throws Exception {
        Customer saved = customerService.addCustomer(customerDto);
        CustomerDto updateDto = new CustomerDto();
        updateDto.setName("Alice Updated");
        updateDto.setEmail("alice123@gmail.com");
        updateDto.setAddress("New Address");
        updateDto.setPhone("9999999999");
        updateDto.setGender("Female");
        updateDto.setRole(Role.customer);

        Customer updated = customerService.updateCustomer(saved.getCustomerId(), updateDto);
        Assertions.assertEquals("Alice Updated", updated.getName());
    }

    
    @Test
    void testDeleteCustomer() throws Exception {
        Customer saved = customerService.addCustomer(customerDto);
        String result = customerService.deleteCustomerById(saved.getCustomerId());
        Assertions.assertEquals("Record deleted successfully", result);
    }


    @Test
    void testGetCustomerByMail() throws Exception {
        Customer saved = customerService.addCustomer(customerDto);
        Customer fetched = customerService.getCustomerByMail(saved.getEmail());
        Assertions.assertEquals("Alice", fetched.getName());
    }

  
    @Test
    void testGetById() throws Exception {
        Customer saved = customerService.addCustomer(customerDto);
        Customer fetched = customerService.getById(saved.getCustomerId());
        Assertions.assertEquals("Alice", fetched.getName());
    }

    
    @Test
    void testGetAllCustomer() throws Exception {
        customerService.addCustomer(customerDto);
        Assertions.assertEquals(1, customerService.getAllCustomer().size());
    }


    @Test
    void testGetAllOrders() throws Exception {
        Customer saved = customerService.addCustomer(customerDto);
        Assertions.assertEquals(0, customerService.getAllOrders(saved.getCustomerId()).size());
    }
}


	    
