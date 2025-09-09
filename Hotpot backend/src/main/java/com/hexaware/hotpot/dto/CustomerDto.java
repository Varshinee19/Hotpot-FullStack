/*
 * 
 * auth: Varshinee
 * DTO class for validation.
 */
package com.hexaware.hotpot.dto;

import com.hexaware.hotpot.entitiesenum.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class CustomerDto {
	
    private int customerId;
	@NotNull
	@NotBlank(message="name is mandatory")
	private String name;
	@NotNull
	@Pattern(regexp="^(Male|Female|Other)$")
	private String gender;
	@Email
	private String email;
	@Pattern(regexp="[0-9]{10}")
	private String phone;
	@NotEmpty(message="address cannot be left empty")
	private String address;
	@NotNull(message="Must mention role")
	private Role role;
	
	 public CustomerDto() {}

	    // Getters and Setters
	    public int getCustomerId() {
	        return customerId;
	    }

	    public void setCustomerId(int customerId) {
	        this.customerId = customerId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role) {
	        this.role = role;
	    }

}
