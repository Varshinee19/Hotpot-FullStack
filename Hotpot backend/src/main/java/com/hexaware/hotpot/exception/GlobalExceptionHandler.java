package com.hexaware.hotpot.exception;
/*
 * 
 * auth: Varshinee
 * Exception handler for handling all exceptions.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexaware.hotpot.controller.CartItemRestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CartItemRestController.class);
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> CustomerNotFoundException() {
		log.error("customer not found");
		return new ResponseEntity<String>("Customer Not Found ", HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<String> CustomerAlreadyExistsException(){
		log.error("customer already exists");
		return new ResponseEntity<String>("Customer already exits ", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<String> DuplicateEmailException(){
		log.error("Email-Id already registered");
		return new ResponseEntity<String>("Email already exsits", HttpStatus.BAD_REQUEST);	
	}
	@ExceptionHandler(MenuNotFoundException.class)
	public ResponseEntity<String> MenuNotFoundException(){
		log.error("Menu Not Found");
		return new ResponseEntity<String>("Menu Does Not Exsist", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(OrderNotExistException.class)
	public ResponseEntity<String> OrderNotExistException(){
		log.error("Order couldnt be found ");
		return new ResponseEntity<String>("Order Could Not Be Found", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(OrderItemNotAvailableException.class)
	public ResponseEntity<String> ItemNotAvailableException(){
		log.error("Item could not be found");
		return new ResponseEntity<String>("Item Not Available", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<String> RestaurantNotFoundException(){
		log.error("Restaurant does not exist");
		return new ResponseEntity<String>("The Restaurant could not be found", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(RestaurantAlreadyExistsException.class)
	public ResponseEntity<String> RestaurantAlreadyExistException(){
		log.error("Restaurant Already there");
		return new ResponseEntity<String>("The Restaurant already Exists", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<String> InvalidQuantityException(){
		log.error("quantity must be above 0");
		return new ResponseEntity<String>("Quantity should not be negative", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CartDoesNotExistException.class)
	public ResponseEntity<String> CartDoesNotExistException(){
		log.error("Cart does not exist");
		return new ResponseEntity<String>("Cart Does Not Exsist", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CartAlreadyExistsException.class)
	public ResponseEntity<String>CartAlreadyExistsException(){
		log.error("Cart Already Exists");
		return new ResponseEntity<String>("Already there is a active cart.", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CartItemNotFoundException.class)
	
	public ResponseEntity<String> CartItemNotFoundException(){
		log.error("cart item not found");
		return new ResponseEntity<String>("Cart Item could not be found.", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CategoryDoesNotExistException.class)
	public ResponseEntity<String> CategoryDoesNotExistException(){
		log.error("category not found");
		return new ResponseEntity<String>("Category is not present.", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<String> PaymentNotFoundException(){
		log.error("Payment Not Found");
		return new ResponseEntity<String>("Payment could not be fetched.", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> MethodArgumentNotValidException(){
		log.error("The validation condition has failed please recheck input ");
		return new ResponseEntity<String>("Invalid Input Format.", HttpStatus.BAD_REQUEST);
	}
	
	

}
