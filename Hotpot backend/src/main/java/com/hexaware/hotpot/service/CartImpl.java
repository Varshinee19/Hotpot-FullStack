package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Implementation Class.
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.exception.CartAlreadyExistsException;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.CustomerRepository;
@Service
public class CartImpl implements ICartService {
	
	@Autowired
	CartRepository repo;
	
	@Autowired
	CustomerRepository customerRepo;

	@Override
	public Cart createCart(int customerId) throws CartAlreadyExistsException, CustomerNotFoundException {
		// TODO Auto-generated method stub
		Customer customer=customerRepo.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found with ID "+customerId));
		if(!(customer.getCart()==null)) {
			throw new CartAlreadyExistsException();
		}
		Cart cart=new Cart();
		cart.setCustomer(customer);
		return repo.save(cart);
	}

	@Override
	public Cart getCartById(int cartId) throws CartDoesNotExistException {
		// TODO Auto-generated method stub
		return repo.findById(cartId).orElseThrow(()->new CartDoesNotExistException());
	}

	@Override
	public String clearCart(int cartId) throws CartDoesNotExistException {
		Cart cart=repo.findById(cartId).orElseThrow(()->new CartDoesNotExistException());
	    Customer customer = cart.getCustomer();
	    if (customer != null) {
	        customer.setCart(null);
	        customerRepo.save(customer);
	    }
		repo.deleteById(cartId);
		return "Cart deleted";
	}

	@Override
	public List<Cart> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Cart> getCartByCustomer(int customerId){
		return repo.findByCustomerCustomerId(customerId);

	}

}
