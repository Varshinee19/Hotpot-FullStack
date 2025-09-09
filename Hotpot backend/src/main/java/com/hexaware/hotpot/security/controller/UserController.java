package com.hexaware.hotpot.security.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hotpot.controller.CustomerRestController;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entitiesenum.Role;
import com.hexaware.hotpot.security.dto.AuthRequest;
import com.hexaware.hotpot.security.service.CustomerService;
import com.hexaware.hotpot.security.service.JwtService;
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private CustomerService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(CustomerRestController.class);

    @PostMapping("/registration/new")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer,@RequestHeader(value="Authorization",required=false) String authHeader){
    	Role requestedRole=customer.getRole();
    	if(requestedRole==Role.Admin) {
    		if(authHeader==null||!authHeader.startsWith("Bearer ")) {
    			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create admin accounts");
    		}
    		String token=authHeader.substring(7);
    		List<String> callerRoles=jwtService.extractRoles(token);
    		if(callerRoles==null||!callerRoles.contains("ROLE_ADMIN")) {
    			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("only Admins can create Admin");
    		}
    	}
    	return ResponseEntity.ok(service.saveCustomer(customer));
    	
    	
    
      
       
    }

    @PostMapping("/login/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        String token = null;

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            token = jwtService.generateToken(userDetails);
            
            logger.info("Token generated: " + token);
        } else {
            logger.info("Invalid credentials");
            throw new UsernameNotFoundException("Email or Password is invalid");
        }

        return token; 
    }
}
	


