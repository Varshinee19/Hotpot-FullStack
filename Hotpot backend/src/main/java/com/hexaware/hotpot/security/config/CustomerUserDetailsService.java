package com.hexaware.hotpot.security.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.security.repository.CustomerSecurityRepository;
@Service
public class CustomerUserDetailsService implements UserDetailsService{
	@Autowired
	CustomerSecurityRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Customer>customer=repo.findByEmail(email);
		return customer.map(CustomerUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found "));

	}
   

}
